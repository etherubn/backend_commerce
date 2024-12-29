package com.catdog.comerce.dto.request;


import com.catdog.comerce.enums.PetType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@AllArgsConstructor
@Data
@NoArgsConstructor
@JsonDeserialize(using = UpdateProductoDtoDeserializer.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateProductDto {

    protected Long idProduct;

    @Size(max = 50,message = "Code by brach accepts a maximum of 50 characters")
    @JsonProperty("code_branch")
    protected String codeByBranch;

    @Size(min = 2,max = 30,message = "Product's name needs to contain min 2 and max 30 characters")
    protected String name;

    @Positive(message = "Price must be greater than 0")
    protected BigDecimal price;

    @Positive(message = "stock must be greater than 0")
    protected Integer stock;

    @Size(max = 50,message = "Description allowed max 50 characters")
    protected String description;

    protected CategoryDto category;

    protected BrandDto brand;

    @JsonProperty("pet_type")
    protected PetType petType;

    @JsonProperty("image_product")
    protected String imageProduct;
}
