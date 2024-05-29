package com.siki.product.service;

import com.siki.product.dto.PaymentDto;
import jakarta.servlet.http.HttpServletRequest;

public interface PaymentService {
    PaymentDto.VNPayResponse createVNPayPayment(HttpServletRequest request);
}
