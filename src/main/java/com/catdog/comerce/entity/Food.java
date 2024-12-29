package com.catdog.comerce.entity;

import com.catdog.comerce.enums.FoodType;
import com.catdog.comerce.enums.PetType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Entity
@EqualsAndHashCode(callSuper=true)
@AllArgsConstructor
@NoArgsConstructor
@DiscriminatorValue("FOOD")
public class Food extends Product{

    public Food(Long idProduct, String productCode, String codeByBranch, String name, BigDecimal price, Integer stock, Integer quantitySale, String description, Category category, Brand brand, PetType petType, String imageProduct, Boolean refrigeration, Double weight, FoodType foodType) {
        super(idProduct, productCode, codeByBranch, name, price, stock, quantitySale, description, category, brand, petType, imageProduct);
        this.refrigeration = refrigeration;
        this.weight = weight;
        this.foodType = foodType;
    }

    @NotNull
    private Boolean refrigeration;

    //TODO : Ver validacion para 2 decimales
    @Positive
    @NotNull
    private Double weight;

    @NotNull
    @Enumerated(EnumType.STRING)
    private FoodType foodType;
}
