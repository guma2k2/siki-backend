package com.payment.controller;

import com.payment.dto.PaymentDto;
import com.payment.service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;
    @GetMapping("/vn-pay")
    public ResponseEntity<PaymentDto.VNPayResponse> pay(HttpServletRequest request) {
        return ResponseEntity.ok().body(paymentService.createVNPayPayment(request));
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
}
