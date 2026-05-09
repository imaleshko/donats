package com.donats.backend.donation;

import com.donats.backend.donation.dto.DonationInitRequest;
import com.donats.backend.donation.dto.DonationInitResponse;
import com.donats.backend.donation.dto.DonationView;
import com.donats.backend.donation.exceptions.DonationCloseException;
import com.donats.backend.donation.liqpay.LiqPayService;
import com.donats.backend.entities.UserEntity;
import com.donats.backend.fundraising.FundraisingEntity;
import com.donats.backend.fundraising.FundraisingRepository;
import com.donats.backend.fundraising.page.FundraisingNotFoundException;
import com.donats.backend.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class DonationService {

    private final DonationRepository donationRepository;
    private final FundraisingRepository fundraisingRepository;
    private final UserRepository userRepository;
    private final LiqPayService liqPayService;

    public DonationService(DonationRepository donationRepository, FundraisingRepository fundraisingRepository, UserRepository userRepository, LiqPayService liqPayService) {
        this.donationRepository = donationRepository;
        this.fundraisingRepository = fundraisingRepository;
        this.userRepository = userRepository;
        this.liqPayService = liqPayService;
    }

    @Transactional
    public DonationInitResponse initDonation(DonationInitRequest request, String email) {
        FundraisingEntity fundraising = fundraisingRepository.findById(request.fundraisingId())
                .orElseThrow(() -> new FundraisingNotFoundException("Збір не знайдено"));

        UserEntity user = null;
        if (email != null) {
            user = userRepository.findByEmail(email).orElse(null);
        }

        String orderId = "Donation" + UUID.randomUUID();

        DonationEntity donation = new DonationEntity();
        donation.setAmount(request.amount());
        donation.setName(request.name());
        donation.setMessage(request.message());
        donation.setFundraising(fundraising);
        donation.setUser(user);
        donation.setStatus(DonationStatusEnum.PENDING);
        donation.setOrderId(orderId);

        donationRepository.save(donation);

        String description = "Донат на збір: " + fundraising.getTitle();

        return liqPayService.generateLiqPayParams(request.amount(), orderId, description);
    }

    @Transactional
    public void closeDonation(String data, String signature) {
        if (!liqPayService.verifySignature(data, signature)) {
            throw new DonationCloseException("Помилка завершення донату");
        }
        Map<String, Object> payload = liqPayService.decodeData(data);
        String orderId = (String) payload.get("order_id");
        String status = (String) payload.get("status");

        DonationEntity donation = donationRepository.findByOrderId(orderId)
                .orElseThrow(() -> new DonationCloseException("Донат не знайдено"));

        if ("success".equals(status)) {
            if (donation.getStatus() != DonationStatusEnum.SUCCESS) {
                donation.setStatus(DonationStatusEnum.SUCCESS);

                FundraisingEntity fundraising = donation.getFundraising();
                fundraising.setBalance(fundraising.getBalance().add(donation.getAmount()));

                fundraisingRepository.save(fundraising);
                donationRepository.save(donation);
            }
        } else if ("error".equals(status) || "failure".equals(status) || "reversed".equals(status)) {
            if (donation.getStatus() != DonationStatusEnum.FAILED) {
                donation.setStatus(DonationStatusEnum.FAILED);
                donationRepository.save(donation);
            }
        }
    }

    @Transactional(readOnly = true)
    public List<DonationView> getSuccessfulDonations(Long fundraisingId) {
        return donationRepository
                .findAllByFundraisingIdAndStatusOrderByCreatedAtDesc(fundraisingId, DonationStatusEnum.SUCCESS)
                .stream()
                .map(DonationView::from)
                .toList();
    }
}
