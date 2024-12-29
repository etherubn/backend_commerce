package com.catdog.comerce.dto.request;

import com.catdog.comerce.enums.AccesoryType;
import com.catdog.comerce.enums.PetType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccesoryDto extends ProductDto{
    public AccesoryDto(Long idProduct, String productCode,Integer quantitySale, String codeByBranch, String name, BigDecimal price, Integer stock, String description, CategoryDto category, BrandDto brand, PetType petType, String imageProduct, Boolean hypoallergenic, AccesoryType accesoryType) {
        super(idProduct, productCode, quantitySale, codeByBranch, name, price, stock, description, category, brand, petType, imageProduct);
        this.hypoallergenic = hypoallergenic;
        this.accesoryType = accesoryType;
    }

    @NotNull(message = "Characteristic of whether it is hypoallergenic is required")
    private Boolean hypoallergenic;

    @NotNull(message = "Accesory's type is required")
    @Enumerated(EnumType.STRING)
    @JsonProperty("accesory_type")
    private AccesoryType accesoryType;
}
