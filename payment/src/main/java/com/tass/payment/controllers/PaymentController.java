package com.tass.payment.controllers;

import com.tass.common.model.ApplicationException;
import com.tass.common.model.BaseResponse;
import com.tass.common.model.payment.request.OrderPaymentRequest;
import com.tass.common.model.payment.response.OrderPaymentResponse;
import com.tass.payment.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transaction")
public class PaymentController {

    @Autowired
    PaymentService paymentService;

    @PostMapping
    public OrderPaymentResponse createTranaction(@RequestBody OrderPaymentRequest request) throws
        ApplicationException{

        return paymentService.createOrderPayment(request);
    }

    @PutMapping("/transId")
    public BaseResponse createTranaction(@PathVariable(name = "transId") long transactionId) throws
        ApplicationException{

        return paymentService.rollbackOrderPayment(transactionId);
    }
    // rollback user

}
