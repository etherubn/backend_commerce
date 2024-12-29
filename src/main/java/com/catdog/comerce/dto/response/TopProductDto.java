package com.catdog.comerce.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TopProductDto {
    @JsonProperty("id")
    private Long idProduct;
    private String name;
    @JsonProperty("image_product")
    private String imageProduct;

}
