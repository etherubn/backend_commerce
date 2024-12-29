package com.catdog.comerce.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseProductSellingDto {
    @JsonProperty("id")
    private Long idProduct;
    @JsonProperty("product_code")
    private String productCode;
    private String product;
    private BigDecimal price;
    private Integer quantity;
    private BigDecimal subtotal;
}
