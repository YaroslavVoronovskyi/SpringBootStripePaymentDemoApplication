package com.gmail.voronovskyi.yaroslav.isg.stripe.service.impl;

import com.gmail.voronovskyi.yaroslav.isg.stripe.dto.CapturePaymentResponse;
import com.gmail.voronovskyi.yaroslav.isg.stripe.dto.CreatePaymentRequest;
import com.gmail.voronovskyi.yaroslav.isg.stripe.dto.CreatePaymentResponse;
import com.gmail.voronovskyi.yaroslav.isg.stripe.dto.StripeResponse;
import com.gmail.voronovskyi.yaroslav.isg.stripe.service.StripeService;
import com.gmail.voronovskyi.yaroslav.isg.stripe.util.Constant;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class StripeServiceImpl implements StripeService {

    @Value("${stripe.secretKey}")
    private final String secretKey;

    @Override
    public StripeResponse createPayment(CreatePaymentRequest createPaymentRequest) {
        Stripe.apiKey = secretKey;

        SessionCreateParams.LineItem.PriceData.ProductData productData =
                SessionCreateParams.LineItem.PriceData.ProductData.builder()
                        .setName(createPaymentRequest.getName())
                        .build();

        SessionCreateParams.LineItem.PriceData priceData =
                SessionCreateParams.LineItem.PriceData.builder()
                        .setCurrency(createPaymentRequest.getCurrency())
                        .setUnitAmount(createPaymentRequest.getAmount())
                        .setProductData(productData)
                        .build();

        SessionCreateParams.LineItem lineItem =
                SessionCreateParams
                        .LineItem.builder()
                        .setQuantity(createPaymentRequest.getQuantity())
                        .setPriceData(priceData)
                        .build();

        SessionCreateParams params =
                SessionCreateParams.builder()
                        .setMode(SessionCreateParams.Mode.PAYMENT)
                        .setSuccessUrl(createPaymentRequest.getSuccessUrl())
                        .setCancelUrl(createPaymentRequest.getCancelUrl())
                        .addLineItem(lineItem)
                        .build();

        Session session;
        try {
            session = Session.create(params);
        } catch (StripeException e) {
            e.printStackTrace();
            return StripeResponse
                    .builder()
                    .status(Constant.FAILURE)
                    .message(Constant.FAILED_CREATION_MESSAGE)
                    .httpStatus(Constant.HTTP_STATUS_BAD_REQUEST)
                    .data(null)
                    .build();
        }

        CreatePaymentResponse responseData = CreatePaymentResponse
                .builder()
                .sessionId(session.getId())
                .sessionUrl(session.getUrl())
                .build();

        return StripeResponse
                .builder()
                .status(Constant.SUCCESS)
                .message(Constant.SUCCESSFULLY_CREATION_MESSAGE)
                .httpStatus(Constant.HTTP_STATUS_OK)
                .data(responseData)
                .build();
    }

    @Override
    public StripeResponse capturePayment(String sessionId) {
        Stripe.apiKey = secretKey;

        try {
            Session session = Session.retrieve(sessionId);
            String status = session.getStatus();

            if (status.equalsIgnoreCase(Constant.STRIPE_SESSION_STATUS_SUCCESS)) {
                log.info(Constant.SUCCESSFULLY_CAPTURING_MESSAGE);
            }

            CapturePaymentResponse responseData = CapturePaymentResponse
                    .builder()
                    .sessionId(sessionId)
                    .sessionStatus(status)
                    .paymentStatus(session.getPaymentStatus())
                    .build();

            return StripeResponse
                    .builder()
                    .status(Constant.SUCCESS)
                    .message(Constant.SUCCESSFULLY_CAPTURING_MESSAGE)
                    .httpStatus(Constant.HTTP_STATUS_OK)
                    .data(responseData)
                    .build();
        } catch (StripeException exception) {
            exception.printStackTrace();
            return StripeResponse
                    .builder()
                    .status(Constant.FAILURE)
                    .message(Constant.FAILED_CAPTURING_MESSAGE)
                    .httpStatus(Constant.HTTP_STATUS_INTERNAL_SERVER_ERROR)
                    .data(null)
                    .build();
        }
    }
}
