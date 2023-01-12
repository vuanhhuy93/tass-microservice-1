package com.tass.microservice.warehouse.repositories;

import com.tass.microservice.warehouse.entities.ProductWareHouse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductWareHouseRepository extends JpaRepository<ProductWareHouse, Long> {
}
