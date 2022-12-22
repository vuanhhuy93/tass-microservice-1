package com.tass.microservice.order.repositories;

import com.tass.microservice.order.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
