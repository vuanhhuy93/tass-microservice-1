package com.tass.payment.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Table
@Data
public class OrderPaymentEntity {
    @Id
    private long orderId;

    private long amount;

    private int status;
}
