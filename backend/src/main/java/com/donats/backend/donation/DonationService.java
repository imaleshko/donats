package com.donats.backend.donation;

import com.donats.backend.donation.dto.Donation;
import com.donats.backend.donation.dto.DonationInitRequest;
import com.donats.backend.donation.dto.DonationInitResponse;
import com.donats.backend.donation.liqpay.LiqPayService;
import com.donats.backend.exceptions.DonationCloseException;
import com.donats.backend.exceptions.DonationInitException;
import com.donats.backend.exceptions.FundraiserNotFoundException;
import com.donats.backend.fundraiser.FundraiserEntity;
import com.donats.backend.fundraiser.FundraiserRepository;
import com.donats.backend.fundraiser.FundraiserStatus;
import com.donats.backend.user.UserEntity;
import com.donats.backend.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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
    public DonationInitResponse initDonation(DonationInitRequest request, Long userId) {
        FundraiserEntity fundraiser = fundraiserRepository.findById(request.fundraiserId())
                .orElseThrow(() -> new FundraiserNotFoundException("Збір не знайдено"));

        if (fundraiser.getStatus() != FundraiserStatus.ACTIVE) {
            throw new DonationInitException("Збір закритий");
        }

        UserEntity user = null;
        if (userId != null) {
            user = userRepository.findById(userId).orElse(null);
        }

        String orderId = "Donation-" + UUID.randomUUID();

        DonationEntity donation = new DonationEntity();
        donation.setAmount(request.amount());
        donation.setName(request.name());
        donation.setMessage(request.message());
        donation.setFundraiser(fundraiser);
        donation.setUser(user);
        donation.setStatus(DonationStatus.PENDING);
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
        String liqPayStatus = (String) payload.get("status");
        BigDecimal amount = new BigDecimal(payload.get("amount").toString());

        DonationEntity donation = donationRepository.findByOrderId(orderId)
                .orElseThrow(() -> new DonationCloseException("Донат не знайдено"));

        if ("success".equals(liqPayStatus)) {
            if (donation.getStatus() != DonationStatus.SUCCESS) {
                donation.setAmount(amount);
                donation.setStatus(DonationStatus.SUCCESS);

                FundraiserEntity fundraiser = donation.getFundraiser();
                BigDecimal balanceAfterDonation = fundraiser.getBalance().add(amount);
                fundraiser.setBalance(balanceAfterDonation);

                if (fundraiser.getGoal() != null && balanceAfterDonation.compareTo(fundraiser.getGoal()) >= 0) {
                    fundraiser.setStatus(FundraiserStatus.CLOSED);
                }
                
                fundraiserRepository.save(fundraiser);
                donationRepository.save(donation);
            }
        } else if ("error".equals(liqPayStatus) || "failure".equals(liqPayStatus) || "reversed".equals(liqPayStatus)) {
            if (donation.getStatus() != DonationStatus.FAILED) {
                donation.setStatus(DonationStatus.FAILED);
                donationRepository.save(donation);
            }
        }
    }

    @Transactional(readOnly = true)
    public List<Donation> getSuccessfulDonations(Long fundraiserId) {
        return donationRepository
                .findAllByFundraiserIdAndStatusOrderByCreatedAtDesc(fundraiserId, DonationStatus.SUCCESS)
                .stream()
                .map(Donation::from)
                .toList();
    }
}
