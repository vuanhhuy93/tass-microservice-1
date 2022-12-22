package com.tass.microservice.order.connector;

import com.tass.common.model.BaseResponseV2;
import com.tass.common.model.dto.product.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "product-service" , url ="${tass.product.address}")
public interface ProductConnector {

    @GetMapping("/api/{id}")
    BaseResponseV2<ProductDTO> getProductById(@PathVariable long id);
}
