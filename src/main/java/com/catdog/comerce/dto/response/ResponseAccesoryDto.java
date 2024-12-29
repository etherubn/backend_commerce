package com.catdog.comerce.dto.response;

import com.catdog.comerce.enums.AccesoryType;
import com.catdog.comerce.enums.PetType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseAccesoryDto extends ResponseProductDto{
    public ResponseAccesoryDto(Long idProduct, String productCode, Integer quantitySale, String codeByBranch, String name, BigDecimal price, Integer stock, String description, ResponseCategoryDto category, ResponseBrandDto brand, PetType petType, String imageProduct, Boolean hypoallergenic, AccesoryType accesoryType) {
        super(idProduct, productCode, quantitySale, codeByBranch, name, price, stock, description, category, brand, petType, imageProduct);
        this.hypoallergenic = hypoallergenic;
        this.accesoryType = accesoryType;
    }

    private Boolean hypoallergenic;
    @JsonProperty("accesory_type")
    private AccesoryType accesoryType;
}
