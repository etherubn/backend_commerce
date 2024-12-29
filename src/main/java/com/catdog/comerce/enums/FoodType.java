package com.catdog.comerce.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum FoodType {
    DRY("dry"),
    WET("wet");

    private String value;

    private FoodType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static FoodType fromValue(String value){
        if (value!=null){
            for (FoodType foodType:FoodType.values()){
                if (value.equals(foodType.getValue())){
                    return foodType;
                }
            }
        }

        throw new IllegalArgumentException("Valor no se encuentra dentro de los valores permitidos: "+value);
    }
}
