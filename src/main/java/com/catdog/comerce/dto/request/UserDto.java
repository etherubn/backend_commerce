package com.catdog.comerce.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto implements Serializable {
    @JsonProperty("id")
    private Long idUser;
    @NotBlank(message = "address is required")
    @Size(min = 5, max = 50,message = "Addres required between 5 or 50 characters")
    private String address;
    @NotBlank(message = "Name is required")
    @Size(max = 30,message = "Name needs 30 characters")
    private String name;
    @NotBlank(message ="Last name is required")
    @Size(max = 30,message = "Last name needs 30 characters")
    @JsonProperty("last_name")
    private String lastName;
    @NotBlank(message = "Dni is required")
    @Size(min = 8,max = 8,message = "Dno needs only 8 characters")
    private String dni;
}
