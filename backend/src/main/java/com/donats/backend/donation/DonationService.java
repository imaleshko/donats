package com.donats.backend.donation;

import com.donats.backend.donation.dto.DonationInitRequest;
import com.donats.backend.donation.dto.DonationInitResponse;
import com.donats.backend.donation.dto.DonationView;
import com.donats.backend.donation.exceptions.DonationCloseException;
import com.donats.backend.donation.liqpay.LiqPayService;
import com.donats.backend.entities.UserEntity;
import com.donats.backend.fundraiser.FundraiserEntity;
import com.donats.backend.fundraiser.FundraiserNotFoundException;
import com.donats.backend.fundraiser.FundraiserRepository;
import com.donats.backend.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class DonationService {

    private final DonationRepository donationRepository;
    private final FundraiserRepository fundraiserRepository;
    private final UserRepository userRepository;
    private final LiqPayService liqPayService;

    public DonationService(DonationRepository donationRepository, FundraiserRepository fundraiserRepository, UserRepository userRepository, LiqPayService liqPayService) {
        this.donationRepository = donationRepository;
        this.fundraiserRepository = fundraiserRepository;
        this.userRepository = userRepository;
        this.liqPayService = liqPayService;
    }

    @Transactional
    public DonationInitResponse initDonation(DonationInitRequest request, String email) {
        FundraiserEntity fundraiser = fundraiserRepository.findById(request.fundraiserId())
                .orElseThrow(() -> new FundraiserNotFoundException("Збір не знайдено"));

        UserEntity user = null;
        if (email != null) {
            user = userRepository.findByEmail(email).orElse(null);
        }

        String orderId = "Donation" + UUID.randomUUID();

        DonationEntity donation = new DonationEntity();
        donation.setAmount(request.amount());
        donation.setName(request.name());
        donation.setMessage(request.message());
        donation.setFundraiser(fundraiser);
        donation.setUser(user);
        donation.setStatus(DonationStatusEnum.PENDING);
        donation.setOrderId(orderId);

        donationRepository.save(donation);

        String description = "Донат на збір: " + fundraiser.getTitle();

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

                FundraiserEntity fundraiser = donation.getFundraiser();
                fundraiser.setBalance(fundraiser.getBalance().add(donation.getAmount()));

                fundraiserRepository.save(fundraiser);
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
    public List<DonationView> getSuccessfulDonations(Long fundraiserId) {
        return donationRepository
                .findAllByFundraiserIdAndStatusOrderByCreatedAtDesc(fundraiserId, DonationStatusEnum.SUCCESS)
                .stream()
                .map(DonationView::from)
                .toList();
    }
}
