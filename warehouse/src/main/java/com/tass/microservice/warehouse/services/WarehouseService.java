package com.tass.microservice.warehouse.services;

import com.tass.common.model.ApplicationException;
import com.tass.common.model.BaseResponse;
import com.tass.common.model.ERROR;
import com.tass.common.model.warehouse.dto.CreatedTransactionData;
import com.tass.common.model.warehouse.request.CreateTransactionRequest;
import com.tass.common.model.warehouse.request.RollbackTransactionRequest;
import com.tass.common.model.warehouse.response.CreateTransactionResponse;
import com.tass.microservice.warehouse.domain.ProductWareHouseDomain;
import com.tass.microservice.warehouse.entities.OrderTransaction;
import com.tass.microservice.warehouse.entities.ProductWareHouse;
import com.tass.microservice.warehouse.repositories.OrderTransactionRepository;
import com.tass.microservice.warehouse.repositories.ProductWareHouseRepository;
import java.util.Optional;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j2
public class WarehouseService {

    @Autowired
    ProductWareHouseRepository productWareHouseRepository;

    @Autowired
    OrderTransactionRepository orderTransactionRepository;

    @Autowired
    ProductWareHouseDomain productWareHouseDomain;
    @Transactional
    public CreateTransactionResponse createTransactionResponse(CreateTransactionRequest request)
        throws
        ApplicationException {

        log.info("created warehouse product transaction- {}", request);

        this.validateRequest(request);

        // check kho
        ProductWareHouse productWareHouse = productWareHouseDomain.getProductWareHouseInfoByProductId(
            request.getProductId());

        productWareHouseDomain.validateWarehouseTotal(productWareHouse , request.getTotal());

        productWareHouse.setTotal(productWareHouse.getTotal() - request.getTotal());

        productWareHouseRepository.save(productWareHouse);

        OrderTransaction orderTransaction = new OrderTransaction(request);

        orderTransactionRepository.save(orderTransaction);

        CreateTransactionResponse response = new CreateTransactionResponse();

        CreatedTransactionData data = new CreatedTransactionData();
        data.setTransactionId(orderTransaction.getId());

        response.setData(data);
        log.info("created warehouse product transaction DONE");

        return response;
    }

    @Transactional
    public BaseResponse rollbackTransaction(RollbackTransactionRequest request) throws ApplicationException{

        validateRollbackRequest(request);


        Optional<OrderTransaction> orderTransactionOpt = orderTransactionRepository.findById(
            request.getTransactionId());

        if (orderTransactionOpt.isEmpty()){
            throw new ApplicationException(ERROR.WARE_HOUSE_ORDER_TRANSACTION_INVALID);
        }
        OrderTransaction orderTransaction = orderTransactionOpt.get();
        orderTransaction.setStatus(2);
        orderTransactionRepository.save(orderTransaction);

        ProductWareHouse productWareHouse = productWareHouseDomain.getProductWareHouseInfoByProductId(orderTransaction.getProductId());


        productWareHouse.setTotal(productWareHouse.getTotal() + orderTransaction.getTotal());

        productWareHouseRepository.save(productWareHouse);


        return new BaseResponse();
    }

    private void validateRollbackRequest(RollbackTransactionRequest request) {
        if (request.getOrderId() < 1) {
            log.debug("ware house product rollback transaction fail - orderId invalid");
            throw new ApplicationException(ERROR.INVALID_PARAM, "order id is invalid");
        }

        if (request.getTransactionId() < 1) {
            log.debug("ware house product transaction fail - orderId invalid");
            throw new ApplicationException(ERROR.INVALID_PARAM, "param orderId is invalid");
        }
    }


    private void validateRequest(CreateTransactionRequest request) {
        if (request.getTotal() < 1) {
            log.debug("ware house product transaction fail - total invalid");
            throw new ApplicationException(ERROR.INVALID_PARAM, "param total is invalid");
        }

        if (request.getProductId() < 1) {
            log.debug("ware house product transaction fail - productId invalid");
            throw new ApplicationException(ERROR.INVALID_PARAM, "param productId is invalid");
        }
    }
}
