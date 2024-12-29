package com.catdog.comerce.dto.request;

import com.catdog.comerce.enums.SellingState;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SellingStateDto {
    @NotNull(message = "order state is required")
    @JsonProperty("selling_state")
    private SellingState sellingState;
}
