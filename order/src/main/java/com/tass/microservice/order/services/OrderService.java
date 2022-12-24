package com.tass.microservice.order.services;

import com.tass.common.model.ApplicationException;
import com.tass.common.model.BaseResponse;
import com.tass.common.model.BaseResponseV2;
import com.tass.common.model.ERROR;
import com.tass.common.model.constans.ORDER;
import com.tass.common.model.dto.product.ProductDTO;
import com.tass.common.model.userauthen.UserDTO;
import com.tass.microservice.order.connector.ProductConnector;
import com.tass.microservice.order.entities.Order;
import com.tass.microservice.order.model.request.CreatedOrderRequest;
import com.tass.microservice.order.repositories.OrderRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class OrderService extends BaseService {
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProductConnector productConnector;

    @Autowired
    @Qualifier("order")
    ChannelTopic channelTopic;

    @Autowired
    ResdisPusherMessageService resdisPusherMessageService;

    public BaseResponse createdOrder(CreatedOrderRequest request) throws ApplicationException{

        log.info("created order with request {}" , request);

        // step 1 : validate request

        UserDTO userDTO = getUserDTO();

        if (request.getProductId() < 1 || request.getTotal() < 1){
            throw new ApplicationException(ERROR.INVALID_PARAM);
        }
        // step 2 : validate product
        BaseResponseV2<ProductDTO> productInfoResponse = productConnector.getProductById(
            request.getProductId());

        if (!productInfoResponse.isSuccess()){
            throw new ApplicationException(ERROR.INVALID_PARAM);
        }
        ProductDTO productDTO = productInfoResponse.getData();

        if (productDTO == null){
            throw new ApplicationException(ERROR.INVALID_PARAM);
        }

        if (productDTO.getStatus() != 1){

            throw new ApplicationException(ERROR.PRODUCT_NOT_ACTIVE);
        }

        Order order = new Order();

        order.setIsSuccess(ORDER.SUCCESS_STATUS.FAIL);
        order.setUserId(userDTO.getUserId());
        order.setStatus(ORDER.STATUS.CREATED);
        order.setProductId(request.getProductId());
        order.setTotalItems(request.getTotal());

        orderRepository.save(order);

        // send event created order
        resdisPusherMessageService.sendMessage("created order with id" + order.getId() , channelTopic);

        return new BaseResponse();
    }
}
