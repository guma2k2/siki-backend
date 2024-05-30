package com.payment.service;

import com.payment.dto.PaymentDto;
import jakarta.servlet.http.HttpServletRequest;

public interface PaymentService {
    PaymentDto.VNPayResponse createVNPayPayment(HttpServletRequest request);
}
