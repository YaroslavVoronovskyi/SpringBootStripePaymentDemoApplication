package com.gmail.voronovskyi.yaroslav.isg.stripe.service;

import com.gmail.voronovskyi.yaroslav.isg.stripe.dto.CreatePaymentRequest;
import com.gmail.voronovskyi.yaroslav.isg.stripe.dto.StripeResponse;

public interface StripeService {

    StripeResponse createPayment(CreatePaymentRequest createPaymentRequest);

    StripeResponse capturePayment(String sessionId);
}
