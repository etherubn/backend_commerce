package com.catdog.comerce.entity;

import com.catdog.comerce.enums.HygieneType;
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
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=true)
@DiscriminatorValue("HYGIENE")
public class Hygiene extends Product {
    public Hygiene(Long idProduct, String productCode, String codeByBranch, String name, BigDecimal price, Integer stock, Integer quantitySale, String description, Category category, Brand brand, PetType petType, String imageProduct, Double volume, HygieneType hygieneType) {
        super(idProduct, productCode, codeByBranch, name, price, stock, quantitySale, description, category, brand, petType, imageProduct);
        this.volume = volume;
        this.hygieneType = hygieneType;
    }

    @NotNull
    @Positive
    private Double volume;

    @NotNull
    @Enumerated(EnumType.STRING)
    private HygieneType hygieneType;
}
