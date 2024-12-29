package com.catdog.comerce.dto.response;

import com.catdog.comerce.enums.RoleType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseRoleDto {
    @JsonProperty("id")
    private Long idRole;
    private RoleType type;
}
