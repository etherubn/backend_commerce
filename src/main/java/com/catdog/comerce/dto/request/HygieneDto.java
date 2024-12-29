package com.catdog.comerce.dto.request;

import com.catdog.comerce.enums.HygieneType;
import com.catdog.comerce.enums.PetType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HygieneDto extends ProductDto{
    public HygieneDto(Long idProduct, String productCode,Integer quantitySale, String codeByBranch, String name, BigDecimal price, Integer stock, String description, CategoryDto category, BrandDto brand, PetType petType, String imageProduct, Double volume, HygieneType hygieneType) {
        super(idProduct, productCode, quantitySale,codeByBranch, name, price, stock, description, category, brand, petType, imageProduct);
        this.volume = volume;
        this.hygieneType = hygieneType;
    }

    @NotNull(message = "Volume is required")
    @Positive(message = "Volume must be positive")
    private Double volume;

    @NotNull(message = "Hygiene type is required")
    @Enumerated(EnumType.STRING)
    @JsonProperty("hygiene_type")
    private HygieneType hygieneType;
}
