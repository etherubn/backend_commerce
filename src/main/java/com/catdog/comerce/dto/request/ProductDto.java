package com.catdog.comerce.dto.request;


import com.catdog.comerce.enums.PetType;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import lombok.*;

import java.math.BigDecimal;



@AllArgsConstructor
@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(using = ProductoDtoDeserializer.class)
public class ProductDto {

    protected Long idProduct;
    @JsonProperty("product_code")
    protected String productCode;

    @JsonProperty("quantity_sale")
    protected Integer quantitySale;

    @NotBlank(message = "Code by Branch is required")
    @Size(max = 50,message = "Code by brach accepts a maximum of 50 characters")
    @JsonProperty("code_branch")
    protected String codeByBranch;

    @Size(min = 2,max = 30,message = "Product's name needs to contain min 2 and max 30 characters")
    @NotBlank(message = "Name is required")
    protected String name;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be greater than 0")
    protected BigDecimal price;

    @NotNull(message = "Stock is required")
    @Positive(message = "stock must be greater than 0")
    protected Integer stock;

    @NotBlank(message = "Description is required")
    @Size(max = 50,message = "Description allowed max 50 characters")
    protected String description;

    @NotNull(message = "category is required")
    protected CategoryDto category;

    @NotNull(message = "brand is required")
    protected BrandDto brand;

    @NotNull(message = "Type cant be null")
    @JsonProperty("pet_type")
    protected PetType petType;

    @NotBlank(message = "Image is required")
    @JsonProperty("image_product")
    protected String imageProduct;
}
