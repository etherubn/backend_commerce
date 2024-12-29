package com.catdog.comerce.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseUserSellingDto {
    @JsonProperty("id")
    private Long idCustomer;
    private String name;
    @JsonProperty("last_name")
    private String lastName;
    private String email;
    private String username;
}
