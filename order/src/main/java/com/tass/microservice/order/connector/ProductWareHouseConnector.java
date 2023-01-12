package com.tass.microservice.order.connector;

import com.tass.common.model.BaseResponse;
import com.tass.common.model.warehouse.request.CreateTransactionRequest;
import com.tass.common.model.warehouse.request.RollbackTransactionRequest;
import com.tass.common.model.warehouse.response.CreateTransactionResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "warehouse-service" , url ="${tass.warehouse.address}")
public interface ProductWareHouseConnector {

    @PostMapping("/transaction")
    CreateTransactionResponse createTransaction(@RequestBody CreateTransactionRequest request);

    @PutMapping("/transaction")
    BaseResponse rollbackTransaction(@RequestBody RollbackTransactionRequest request);
}
