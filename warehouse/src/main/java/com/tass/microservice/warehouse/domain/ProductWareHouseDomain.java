package com.tass.microservice.warehouse.domain;

import com.tass.common.model.ApplicationException;
import com.tass.common.model.ERROR;
import com.tass.microservice.warehouse.entities.ProductWareHouse;
import com.tass.microservice.warehouse.repositories.ProductWareHouseRepository;
import java.util.Optional;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class ProductWareHouseDomain {
    @Autowired
    private ProductWareHouseRepository productWareHouseRepository;

    public ProductWareHouse getProductWareHouseInfoByProductId(long productId) throws
        ApplicationException{
        Optional<ProductWareHouse> productWareHouseOpt =
            productWareHouseRepository.findById(productId);

        if (productWareHouseOpt.isEmpty()) {
            log.debug("not load warehouse info by productId {}", productId);
            throw new ApplicationException(ERROR.INVALID_PARAM, "param total is invalid");
        }

       return productWareHouseOpt.get();

    }

    public void validateWarehouseTotal(ProductWareHouse productWareHouse , long total) throws ApplicationException{
        if (productWareHouse.getTotal() < total) {
            log.debug("warehouse productID {} - total {} - request total {}", productWareHouse.getProductId(),
                productWareHouse.getTotal(), total);

            throw new ApplicationException(ERROR.TOTAL_PRODUCT_INVALID);
        }
    }
}
