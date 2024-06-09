package com.payment.service.impl;

import com.payment.config.VNPayConfig;
import com.payment.dto.PaymentDto;
import com.payment.dto.PaymentPostDto;
import com.payment.dto.PaymentRequestDto;
import com.payment.model.Payment;
import com.payment.repository.PaymentRepository;
import com.payment.service.PaymentService;
import com.payment.utils.VNPayUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final VNPayConfig vnPayConfig;

    private final PaymentRepository paymentRepository;
    @Override
    public PaymentDto.VNPayResponse createVNPayPayment(PaymentRequestDto request, HttpServletRequest httpServletRequest) {
        long amount = request.amount() * 100L;
        String bankCode = request.bankCode();
        Map<String, String> vnpParamsMap = vnPayConfig.getVNPayConfig(request);
        vnpParamsMap.put("vnp_Amount", String.valueOf(amount));
        if (bankCode != null && !bankCode.isEmpty()) {
            vnpParamsMap.put("vnp_BankCode", bankCode);
        }
        vnpParamsMap.put("vnp_IpAddr", VNPayUtils.getIpAddress(httpServletRequest));
        //build query url
        String queryUrl = VNPayUtils.getPaymentURL(vnpParamsMap, true);
        String hashData = VNPayUtils.getPaymentURL(vnpParamsMap, false);
        queryUrl += "&vnp_SecureHash=" + VNPayUtils.hmacSHA512(vnPayConfig.getSecretKey(), hashData);
        String paymentUrl = vnPayConfig.getVnp_PayUrl() + "?" + queryUrl;
        return PaymentDto.VNPayResponse.builder()
                .code("ok")
                .message("success")
                .paymentUrl(paymentUrl).build();
    }

    @Override
    public void savePayment(PaymentPostDto request) {
        Payment payment = Payment.builder()
                .bankTranNo(request.bankTranNo())
                .payDate(request.payDate())
                .orderId(request.orderId())
                .amount(request.amount())
                .cartType(request.cartType())
                .bankCode(request.bankCode())
                .build();
        paymentRepository.save(payment);
        // Todo : update order Status to success
    }
}
