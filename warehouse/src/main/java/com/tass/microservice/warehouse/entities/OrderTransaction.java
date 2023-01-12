package com.tass.microservice.warehouse.entities;

import com.tass.common.model.warehouse.request.CreateTransactionRequest;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;

@Entity
@Data
public class OrderTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long productId;

    private long orderId;

    private long total;

    private int status;

    public OrderTransaction(){

    }

    public OrderTransaction(CreateTransactionRequest request){
        this.setStatus(1);
        this.setProductId(request.getProductId());
        this.setTotal(request.getTotal());
        this.setOrderId(request.getOrderId());
    }
}
