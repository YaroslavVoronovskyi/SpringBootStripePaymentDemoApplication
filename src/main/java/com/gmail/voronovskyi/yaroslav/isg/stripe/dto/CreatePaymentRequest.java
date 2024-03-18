package com.gmail.voronovskyi.yaroslav.isg.stripe.dto;

import lombok.Data;

@Data
public class CreatePaymentRequest {

    private Long amount;
    private Long quantity;
    private String currency;
    private String name;
    private String successUrl;
    private String cancelUrl;
}
