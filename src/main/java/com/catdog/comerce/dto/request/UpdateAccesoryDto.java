package com.catdog.comerce.dto.request;

import com.catdog.comerce.enums.AccesoryType;
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
public class UpdateAccesoryDto extends UpdateProductDto{

    public UpdateAccesoryDto(Long idProduct, String codeByBranch, String name, BigDecimal price, Integer stock, String description, CategoryDto category, BrandDto brand, PetType petType, String imageProduct, Boolean hypoallergenic, AccesoryType accesoryType) {
        super(idProduct, codeByBranch, name, price, stock, description, category, brand, petType, imageProduct);
        this.hypoallergenic = hypoallergenic;
        this.accesoryType = accesoryType;
    }

    private Boolean hypoallergenic;

    @Enumerated(EnumType.STRING)
    @JsonProperty("accesory_type")
    private AccesoryType accesoryType;
}
