package com.payment.dto;

public record PaymentRequestDto(
        int amount,
        String bankCode,
        Long orderId
) {
}
