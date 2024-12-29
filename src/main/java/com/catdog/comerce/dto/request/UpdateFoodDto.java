package com.catdog.comerce.dto.request;

import com.catdog.comerce.enums.FoodType;
import com.catdog.comerce.enums.PetType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateFoodDto extends UpdateProductDto{

    public UpdateFoodDto(Long idProduct, String codeByBranch, String name, BigDecimal price, Integer stock, String description, CategoryDto category, BrandDto brand, PetType petType, String imageProduct, Boolean refrigeration, Double weight, FoodType foodType) {
        super(idProduct, codeByBranch, name, price, stock, description, category, brand, petType, imageProduct);
        this.refrigeration = refrigeration;
        this.weight = weight;
        this.foodType = foodType;
    }

    private Boolean refrigeration;

    @Positive(message = "Weight must be greather than 0")
    private Double weight;

    @Enumerated(EnumType.STRING)
    @JsonProperty("food_type")
    private FoodType foodType;
}
