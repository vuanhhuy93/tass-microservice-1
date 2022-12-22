package com.tass.microservice.order.model.request;

import lombok.Data;

@Data
public class CreatedOrderRequest {
    private long productId;
    private int total;
}
