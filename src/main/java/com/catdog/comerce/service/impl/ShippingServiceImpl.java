package com.catdog.comerce.service.impl;

import com.catdog.comerce.dto.request.ShippingDto;
import com.catdog.comerce.entity.Shipping;
import com.catdog.comerce.repository.ShippingRepo;
import com.catdog.comerce.service.IShippingService;
import com.catdog.comerce.utils.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class ShippingServiceImpl implements IShippingService {
    private final ShippingRepo shippingRepo;
    private final MapperUtil mapperUtil;

    private final Long SHIPPING_ID = 1L;
    private final BigDecimal DEFAULT_BASE_SHIPPING = BigDecimal.valueOf(10);
    private final BigDecimal DEFAULT_LIMIT_SHIPPING = BigDecimal.valueOf(35);

    @Override
    public ShippingDto getShipping() {
        Shipping shipping = shippingRepo.findById(1L).orElse(new Shipping(SHIPPING_ID,DEFAULT_BASE_SHIPPING,DEFAULT_LIMIT_SHIPPING));
        return mapperUtil.map(shipping, ShippingDto.class);
    }

    @Override
    public BigDecimal calculateShipping(BigDecimal total) {
        ShippingDto shippingDto = getShipping();
        return total.compareTo(shippingDto.getLimitPriceShipping())>=0? BigDecimal.ZERO: shippingDto.getBasePriceShipping();
    }

    @Override
    public ShippingDto updateShipping(ShippingDto shippingDto) {

        if (shippingDto.getBasePriceShipping()!=null && shippingDto.getLimitPriceShipping()!=null) {
            if (shippingDto.getBasePriceShipping().compareTo(shippingDto.getLimitPriceShipping()) > 0) {
                throw new IllegalArgumentException("basePriceShipping must be less than limitPriceShipping");
            }
        }

        ShippingDto shippingDto1 = getShipping();
        if (shippingDto.getBasePriceShipping()!=null) {
            shippingDto1.setBasePriceShipping(shippingDto.getBasePriceShipping());
        }

        if (shippingDto.getLimitPriceShipping()!=null) {
            shippingDto1.setLimitPriceShipping(shippingDto.getLimitPriceShipping());
        }

        Shipping shipping = mapperUtil.map(shippingDto1, Shipping.class);

        return mapperUtil.map(shippingRepo.save(shipping), ShippingDto.class);
    }
}
