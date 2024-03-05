package com.gmail.voronovskyi.yaroslav.isg.stripe.controller;

import com.gmail.voronovskyi.yaroslav.isg.stripe.dto.CreatePaymentRequest;
import com.gmail.voronovskyi.yaroslav.isg.stripe.dto.StripeResponse;
import com.gmail.voronovskyi.yaroslav.isg.stripe.service.StripeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/stripe")
@RequiredArgsConstructor
public class StripeController {

    private final StripeService stripeService;

    @PostMapping("/create-payment")
    public ResponseEntity<StripeResponse> createPayment(@RequestBody CreatePaymentRequest createPaymentRequest) {
        StripeResponse stripeResponse = stripeService.createPayment(createPaymentRequest);
        return ResponseEntity
                .status(stripeResponse.getHttpStatus())
                .body(stripeResponse);
    }

    @GetMapping("/capture-payment")
    public ResponseEntity<StripeResponse> capturePayment(@RequestParam String sessionId) {
        StripeResponse stripeResponse = stripeService.capturePayment(sessionId);
        return ResponseEntity
                .status(stripeResponse.getHttpStatus())
                .body(stripeResponse);
    }
}
