package com.catdog.comerce.dto.request;

import com.catdog.comerce.enums.AccesoryType;
import com.catdog.comerce.enums.HygieneType;
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
public class UpdateHygieneDto extends UpdateProductDto{

    public UpdateHygieneDto(Long idProduct, String codeByBranch, String name, BigDecimal price, Integer stock, String description, CategoryDto category, BrandDto brand, PetType petType, String imageProduct, Double volume, HygieneType hygieneType) {
        super(idProduct, codeByBranch, name, price, stock, description, category, brand, petType, imageProduct);
        this.volume = volume;
        this.hygieneType = hygieneType;
    }

    @Positive(message = "Volume must be positive")
    private Double volume;

    @Enumerated(EnumType.STRING)
    @JsonProperty("hygiene_type")
    private HygieneType hygieneType;
}
