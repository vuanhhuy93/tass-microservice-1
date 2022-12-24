package com.tass.microservice.order.controllers;

import com.tass.common.customanotation.RequireUserLogin;
import com.tass.common.model.ApplicationException;
import com.tass.common.model.BaseResponse;
import com.tass.microservice.order.model.request.CreatedOrderRequest;
import com.tass.microservice.order.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/order")
public class OrderController {

    @Autowired
    OrderService orderService ;

    @PostMapping
    @RequireUserLogin
    public BaseResponse createdOrder(@RequestBody CreatedOrderRequest request) throws
        ApplicationException{
        return orderService.createdOrder(request);
    }
}
