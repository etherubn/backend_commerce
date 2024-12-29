package com.catdog.comerce.dto.request;

import com.catdog.comerce.enums.FoodType;
import com.catdog.comerce.enums.PetType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;


@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FoodDto extends ProductDto{

    public FoodDto(Long idProduct, String productCode,Integer quantitySale, String codeByBranch, String name, BigDecimal price, Integer stock, String description, CategoryDto category, BrandDto brand, PetType petType, String imageProduct, Boolean refrigeration, FoodType foodType, Double weight) {
        super(idProduct, productCode,quantitySale, codeByBranch, name, price, stock, description, category, brand, petType, imageProduct);
        this.refrigeration = refrigeration;
        this.foodType = foodType;
        this.weight = weight;
    }

    @NotNull(message = "refrigeration feature is required")
    private Boolean refrigeration;

    @NotNull(message = "Weight is required")
    @Positive(message = "Weight must be greather than 0")
    private Double weight;

    @NotNull(message = "Food type is required")
    @Enumerated(EnumType.STRING)
    @JsonProperty("food_type")
    private FoodType foodType;
}
