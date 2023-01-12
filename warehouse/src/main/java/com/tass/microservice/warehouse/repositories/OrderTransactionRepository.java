package com.tass.microservice.warehouse.repositories;

import com.tass.microservice.warehouse.entities.OrderTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderTransactionRepository extends JpaRepository<OrderTransaction, Long> {
}
