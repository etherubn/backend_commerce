package com.catdog.comerce.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum RoleType {
    ADMIN("ADMIN"),
    USER("USER");

    private String value;

    RoleType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue(){
        return value;
    }

    @JsonCreator
    public static RoleType fromValue(String value){
        if (value!=null){
            for (RoleType roleType:RoleType.values()){
                if (value.equals(roleType.getValue())){
                    return roleType;
                }
            }
        }

        throw new IllegalArgumentException("Valor no se encuentra dentro de los valores permitidos: "+value);
    }
}
