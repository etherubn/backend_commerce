package com.catdog.comerce.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum SellingState {
    PROCESSING_ORDER("processing order"),
    SENDING_ORDER("sending order"),
    ORDER_DELIVERED("order delivered");

    private String value;

    SellingState(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue(){
        return value;
    }

    @JsonCreator
    public static SellingState fromValue(String value){
        if (value!=null){
            for (SellingState sellingState:SellingState.values()){
                if (value.equals(sellingState.getValue())){
                    return sellingState;
                }
            }
        }

        throw new IllegalArgumentException("Valor no se encuentra dentro de los valores permitidos: "+value);
    }
}
