package com.catdog.comerce.dto.response;

import com.catdog.comerce.enums.CategoryType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseCategoryDto {
    @JsonProperty("id")
    private Long idCategory;
    @JsonProperty("type")
    private CategoryType type;
}
