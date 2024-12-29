package com.catdog.comerce.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@Data
@Builder
@Table(name = "shipping")
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Shipping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long idShipping;
    @PositiveOrZero
    @Column(nullable = false, precision = 5, scale = 2,name = "base_price_shipping")
    private BigDecimal basePriceShipping;
    @PositiveOrZero
    @Column(nullable = false, precision = 5, scale = 2,name = "limit_price_shipping")
    private BigDecimal limitPriceShipping;

    public Shipping(Long idShipping,BigDecimal basePriceShipping, BigDecimal limitPriceShipping) {
        if (basePriceShipping.compareTo(limitPriceShipping)>0){
            throw new IllegalArgumentException("basePriceShipping must be less than limitPriceShipping");
        }
        this.idShipping = idShipping;
        this.basePriceShipping = basePriceShipping;
        this.limitPriceShipping = limitPriceShipping;
    }
}
