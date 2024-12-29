package com.catdog.comerce.dto.request;

import com.catdog.comerce.enums.CategoryType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryDto implements Serializable {

    @JsonProperty("id")
    private Long idCategory;

    @NotNull(message = "Type is required")
    private CategoryType type;
}
