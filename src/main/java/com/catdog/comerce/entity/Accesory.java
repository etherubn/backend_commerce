package com.catdog.comerce.entity;

import com.catdog.comerce.enums.AccesoryType;
import com.catdog.comerce.enums.PetType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
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
@DiscriminatorValue("ACCESORY")
public class Accesory extends Product {

    public Accesory(Long idProduct, String productCode, String codeByBranch, String name, BigDecimal price, Integer stock, Integer quantitySale, String description, Category category, Brand brand, PetType petType, String imageProduct, Boolean hypoallergenic, AccesoryType accesoryType) {
        super(idProduct, productCode, codeByBranch, name, price, stock, quantitySale, description, category, brand, petType, imageProduct);
        this.hypoallergenic = hypoallergenic;
        this.accesoryType = accesoryType;
    }

    @NotNull
    private Boolean hypoallergenic;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AccesoryType accesoryType;
}
