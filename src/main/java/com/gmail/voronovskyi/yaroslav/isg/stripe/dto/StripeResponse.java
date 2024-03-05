package com.gmail.voronovskyi.yaroslav.isg.stripe.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class StripeResponse<T> {

    private String status;
    private String message;
    private Integer httpStatus;
    private T data;
}
