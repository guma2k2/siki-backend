package com.payment.service;

import com.payment.dto.PaymentDto;
import com.payment.dto.PaymentPostDto;
import com.payment.dto.PaymentRequestDto;
import jakarta.servlet.http.HttpServletRequest;

public interface PaymentService {
    PaymentDto.VNPayResponse createVNPayPayment(PaymentRequestDto request, HttpServletRequest httpServletRequest);

    void savePayment(PaymentPostDto request);
}
