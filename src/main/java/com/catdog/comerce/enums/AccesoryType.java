package com.catdog.comerce.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum AccesoryType {
    COLLAR("collar"),
    TOY("toy"),
    BED("bed"),
    CLOTHES("clothes");



    private String value;

    AccesoryType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue(){
        return value;
    }

    @JsonCreator
    public static AccesoryType fromValue(String value){
        if (value!=null){
            for (AccesoryType accesoryType:AccesoryType.values()){
                if (value.equals(accesoryType.getValue())){
                    return accesoryType;
                }
            }
        }
        throw new IllegalArgumentException("Valor no se encuentra dentro de los valores permitidos: "+value);
    }
}
