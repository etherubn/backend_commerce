package com.catdog.comerce.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum HygieneType {
    SHAMPOO("shampoo"),
    FRESHENER("freshener"),
    FRAGANCE("fragance");

    private String value;

    private HygieneType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static HygieneType fromValue(String value){
        if (value!=null){
            for (HygieneType hygieneType:HygieneType.values()){
                if (value.equals(hygieneType.getValue())){
                    return hygieneType;
                }
            }
        }

        throw new IllegalArgumentException("Valor no se encuentra dentro de los valores permitidos: "+value);
    }
}
