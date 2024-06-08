package com.payment.controller;

import com.payment.dto.PaymentDto;
import com.payment.dto.PaymentPostDto;
import com.payment.dto.PaymentRequestDto;
import com.payment.service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;
    @GetMapping("/vn-pay")
    public ResponseEntity<PaymentDto.VNPayResponse> pay(@RequestBody PaymentRequestDto request, HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok().body(paymentService.createVNPayPayment(request, httpServletRequest));
    }
    @GetMapping("/vn-pay-callback")
    public ResponseEntity<PaymentDto.VNPayResponse> payCallbackHandler(@RequestParam String vnp_ResponseCode) {
        if (vnp_ResponseCode.equals("00")) {
            // redirect to some path ...
            return new ResponseEntity<>(new PaymentDto.VNPayResponse("00", "Success", ""), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/vn-pay-success")
    public ResponseEntity<Void> pay(@RequestBody PaymentPostDto request) {
        paymentService.savePayment(request);
        return ResponseEntity.noContent().build();
    }
}
