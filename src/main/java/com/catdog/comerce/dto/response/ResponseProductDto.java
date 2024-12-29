package com.catdog.comerce.dto.response;
import com.catdog.comerce.dto.request.ProductoDtoDeserializer;
import com.catdog.comerce.enums.PetType;
import com.fasterxml.jackson.annotation.JsonInclude;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseProductDto {
    private Long idProduct;
    @JsonProperty("product_code")
    private String productCode;
    @JsonProperty("quantity_sale")
    protected Integer quantitySale;
    @JsonProperty("code_branch")
    private String codeByBranch;
    private String name;
    private BigDecimal price;
    private Integer stock;
    private String description;
    private ResponseCategoryDto category;
    private ResponseBrandDto brand;
    @JsonProperty("pet_type")
    private PetType petType;
    @JsonProperty("image_product")
    private String imageProduct;
}
