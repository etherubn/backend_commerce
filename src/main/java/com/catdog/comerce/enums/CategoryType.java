package com.catdog.comerce.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum CategoryType {
    FOOD("food"),
    ACCESORY("accesory"),
    HYGIENE("hygiene");

    private String value;

    CategoryType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue(){
        return value;
    }

    @JsonCreator
    public static CategoryType fromValue(String value){
        if (value!=null){
            for (CategoryType categoryType:CategoryType.values()){
                if (value.equals(categoryType.getValue())){
                    return categoryType;
                }
            }
        }

        throw new IllegalArgumentException("Valor no se encuentra dentro de los valores permitidos: "+value);
    }
}
