package com.tass.microservice.warehouse.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Data;

@Entity
@Data
public class ProductWareHouse {
    @Id
    private long productId;

    private long total;

    private long totalSell;
}
