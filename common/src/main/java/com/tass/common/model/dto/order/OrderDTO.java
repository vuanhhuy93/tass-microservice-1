package com.tass.common.model.dto.order;

import java.io.Serializable;
import lombok.Data;

@Data
public class OrderDTO implements Serializable {
    private long orderId;
    private long productId;
    private long userId;
    private int total;
    private int status;

    public OrderDTO(){

    }
}
