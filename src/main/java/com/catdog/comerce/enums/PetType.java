package com.catdog.comerce.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum PetType {
    DOG("dog"),
    CAT("cat");

    private String value;

    PetType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue(){
        return value;
    }

    @JsonCreator
    public static PetType fromValue(String value){
        if (value!=null){
            for (PetType petType:PetType.values()){
                if (value.equals(petType.getValue())){
                    return petType;
                }
            }
        }

        throw new IllegalArgumentException("Valor no se encuentra dentro de los valores permitidos: "+value);
    }
}
