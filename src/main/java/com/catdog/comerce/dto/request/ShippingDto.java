package com.catdog.comerce.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ShippingDto {

    @JsonProperty("id")
    private Long idShipping;
    @JsonProperty("base_price")
    @PositiveOrZero
    @Digits(integer = 5, fraction = 2)
    private BigDecimal basePriceShipping;
    @JsonProperty("limit_price")
    @PositiveOrZero
    @Digits(integer = 5, fraction = 2)
    private BigDecimal limitPriceShipping;

}
