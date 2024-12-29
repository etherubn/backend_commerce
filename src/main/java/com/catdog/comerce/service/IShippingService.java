package com.catdog.comerce.service;

import com.catdog.comerce.dto.request.ShippingDto;
import com.catdog.comerce.entity.Shipping;

import java.math.BigDecimal;

public interface IShippingService {
    ShippingDto getShipping();
    BigDecimal calculateShipping(BigDecimal total);
    ShippingDto updateShipping(ShippingDto shippingDto);
}
