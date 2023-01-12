package com.tass.microservice.warehouse.controllers;

import com.tass.common.model.ApplicationException;
import com.tass.common.model.BaseResponse;
import com.tass.common.model.warehouse.request.CreateTransactionRequest;
import com.tass.common.model.warehouse.request.RollbackTransactionRequest;
import com.tass.common.model.warehouse.response.CreateTransactionResponse;
import com.tass.microservice.warehouse.services.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transaction")
public class ProductWareHouseControllers {

    @Autowired
    WarehouseService warehouseService;
    // mot api transaction
    @PostMapping
    public CreateTransactionResponse createTransaction(@RequestBody CreateTransactionRequest request) throws
        ApplicationException{

        return warehouseService.createTransactionResponse(request);
    }

    @PutMapping
    public BaseResponse createTransaction(@RequestBody RollbackTransactionRequest request) throws
        ApplicationException{
        return warehouseService.rollbackTransaction(request);

    }

    // 1 api rollback
}
