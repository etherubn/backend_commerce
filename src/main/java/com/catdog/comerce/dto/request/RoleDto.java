package com.catdog.comerce.dto.request;

import com.catdog.comerce.enums.RoleType;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RoleDto implements Serializable {
    @JsonProperty("id")
    private Long idRole;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Role is required")
    private RoleType type;
}
