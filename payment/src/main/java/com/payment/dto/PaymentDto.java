package com.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;

public class PaymentDto {
    @Builder
    @AllArgsConstructor
    public static class VNPayResponse {
        public String code;
        public String message;
        public String paymentUrl;
    }
}
