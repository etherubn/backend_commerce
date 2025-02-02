package com.catdog.comerce.dto.response;

import com.catdog.comerce.enums.HygieneType;
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
public class ResponseHygieneDto extends ResponseProductDto {
    public ResponseHygieneDto(Long idProduct, String productCode, Integer quantitySale, String codeByBranch, String name, BigDecimal price, Integer stock, String description, ResponseCategoryDto category, ResponseBrandDto brand, PetType petType, String imageProduct, Double volume, HygieneType hygieneType) {
        super(idProduct, productCode, quantitySale, codeByBranch, name, price, stock, description, category, brand, petType, imageProduct);
        this.volume = volume;
        this.hygieneType = hygieneType;
    }

    private Double volume;
    @JsonProperty("hygiene_type")
    private HygieneType hygieneType;

}
