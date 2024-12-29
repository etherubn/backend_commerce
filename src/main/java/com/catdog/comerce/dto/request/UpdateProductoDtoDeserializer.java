package com.catdog.comerce.dto.request;

import com.catdog.comerce.dto.response.ResponseCategoryDto;
import com.catdog.comerce.enums.AccesoryType;
import com.catdog.comerce.enums.FoodType;
import com.catdog.comerce.enums.HygieneType;
import com.catdog.comerce.enums.PetType;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.v3.core.util.Json;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.function.Function;

public class UpdateProductoDtoDeserializer extends JsonDeserializer<UpdateProductDto> {

    @Override
    public UpdateProductDto deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        JsonNode node = p.getCodec().readTree(p);
        JsonNode typeNode = node.get("type");

        if (typeNode == null || typeNode.isNull()) {
            throw new IllegalArgumentException("Type is required");
        }
        String type = typeNode.asText();

        if ("food".equals(type)) {
            UpdateFoodDto foodDto = new UpdateFoodDto();
            getCommonProperties(node,foodDto);
            foodDto.setWeight(getDoubleValue(node,"weight"));
            foodDto.setFoodType(getEnumValue(node,"food_type",value-> FoodType.fromValue(value)));
            foodDto.setRefrigeration(getBooleanValue(node,"refrigeration"));
            return foodDto;
        } else if ("accesory".equals(type)) {
            UpdateAccesoryDto accesoryDto = new UpdateAccesoryDto();
            getCommonProperties(node,accesoryDto);
            accesoryDto.setHypoallergenic(getBooleanValue(node,"hypoallergenic"));
            accesoryDto.setAccesoryType(getEnumValue(node,"accessory_type",value-> AccesoryType.fromValue(value)));
            return accesoryDto;

        } else if ("hygiene".equals(type)) {
            UpdateHygieneDto hygieneDto = new UpdateHygieneDto();
            getCommonProperties(node,hygieneDto);
            hygieneDto.setVolume(getDoubleValue(node,"volume"));
            hygieneDto.setHygieneType(getEnumValue(node,"hygiene_type",value-> HygieneType.fromValue(value)));
            return hygieneDto;
        }else {
            throw new IllegalArgumentException("Type of product is not recognized");
        }

    }

    private void getCommonProperties(JsonNode node,UpdateProductDto updateProductDto) {
        updateProductDto.setCodeByBranch(getStringValue(node,"code_branch"));
        updateProductDto.setName(getStringValue(node,"name"));
        updateProductDto.setPrice(getDecimalValue(node,"price"));
        updateProductDto.setStock(getIntValue(node,"stock"));
        updateProductDto.setDescription(getStringValue(node,"description"));
        updateProductDto.setPetType(getEnumValue(node,"pet_type", PetType::fromValue));
        updateProductDto.setImageProduct(getStringValue(node,"image_product"));

        if (node.has("category")) {
            JsonNode categoryNode = node.get("category");

            if (!categoryNode.has("id")){
                throw new IllegalArgumentException("id category is required");
            }
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setIdCategory(categoryNode.get("id").asLong());
            updateProductDto.setCategory(categoryDto);
        }else {
            updateProductDto.setCategory(null);
        }

        if (node.has("brand")) {
            JsonNode brandNode = node.get("brand");

            if (!brandNode.has("id")){
                throw new IllegalArgumentException("id brand is required");
            }
            BrandDto brandDto = new BrandDto();
            brandDto.setIdBrand(brandNode.get("id").asLong());
            updateProductDto.setBrand(brandDto);
        }else {
            updateProductDto.setBrand(null);
        }
    }
    
    private String getStringValue(JsonNode node,String field) {
        if(field.isBlank()){
            return null;
        }
        return node.has(field) && !node.get(field).isNull() ? node.get(field).asText() : null;
    }

    private Integer getIntValue(JsonNode node,String field) {
        if(field.isBlank()){
            return null;
        }

        return node.has(field) && !node.get(field).isNull() ? node.get(field).asInt() : null;
    }
    
    private Double getDoubleValue(JsonNode node,String field) {
        if(field.isBlank()){
            return null;
        }
        return node.has(field) && !node.get(field).isNull() ? node.get(field).asDouble() : null;
    }

    private Long getLongValue(JsonNode node,String field) {
        if(field.isBlank()){
            return null;
        }
        return node.has(field) && !node.get(field).isNull() ? node.get(field).asLong() : null;
    }

    private BigDecimal getDecimalValue(JsonNode node, String field) {
        if (field.isBlank()) {
            return null;
        }

        return node.has(field) && !node.get(field).isNull() ? new BigDecimal(node.get(field).asText()) : null;
    }

    private Boolean getBooleanValue(JsonNode node,String field) {
        if (field.isBlank()) {
            return null;
        }
        return node.has(field) && !node.get(field).isNull() ? node.get(field).asBoolean() : null;
    }

    private <T> T getEnumValue(JsonNode node, String field, Function<String,T> enumMapper) {
        if (field.isBlank()) {
            return null;
        }

        return node.has(field) && !node.get(field).isNull() ? enumMapper.apply(node.get(field).asText()) : null;
    }

}
