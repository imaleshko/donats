package com.donats.backend.donation.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record DonationInitRequest(
        @NotNull Long fundraiserId,
        @NotNull @DecimalMin("10.0") BigDecimal amount,
        @NotBlank @Size(max = 100, message = "Ім'я не може перевищувати 100 символів") String name,
        @Size(max = 255, message = "Повідомлення не може перевищувати 1000 символів") String message
) {
}
