package com.catdog.comerce.dto.response;

import com.catdog.comerce.enums.FoodType;
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
public class ResponseFoodDto extends ResponseProductDto{

    public ResponseFoodDto(Long idProduct, String productCode, Integer quantitySale, String codeByBranch, String name, BigDecimal price, Integer stock, String description, ResponseCategoryDto category, ResponseBrandDto brand, PetType petType, String imageProduct, Boolean refrigeration, Double weight, FoodType foodType) {
        super(idProduct, productCode, quantitySale, codeByBranch, name, price, stock, description, category, brand, petType, imageProduct);
        this.refrigeration = refrigeration;
        this.weight = weight;
        this.foodType = foodType;
    }

    private Boolean refrigeration;
    private Double weight;
    @JsonProperty("food_type")
    private FoodType foodType;
}
