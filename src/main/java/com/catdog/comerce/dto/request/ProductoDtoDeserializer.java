package com.catdog.comerce.dto.request;

import com.catdog.comerce.enums.AccesoryType;
import com.catdog.comerce.enums.FoodType;
import com.catdog.comerce.enums.HygieneType;
import com.catdog.comerce.enums.PetType;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashSet;

import java.util.Set;

public class ProductoDtoDeserializer extends JsonDeserializer<ProductDto> {

    @Override
    public ProductDto deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        JsonNode node = p.getCodec().readTree(p);

        Set<String> missingFields = new HashSet<>();
        String type = getFieldError(node,"type",missingFields,String.class);

        if ("food".equals(type)) {
            FoodDto foodDto = new FoodDto();
            setCommomFields(foodDto, node,missingFields);

            foodDto.setWeight(getFieldError(node,"weight",missingFields,Double.class));
            foodDto.setFoodType(FoodType.fromValue(getFieldError(node,"food_type",missingFields,String.class)));
            foodDto.setRefrigeration(getFieldError(node,"refrigeration",missingFields, Boolean.class));

            if (!missingFields.isEmpty()){
                throw new IllegalArgumentException("Missing fields: " + missingFields);
            }
            return foodDto;
        } else if ("accesory".equals(type)) {
            AccesoryDto accesoryDto = new AccesoryDto();
            setCommomFields(accesoryDto, node,missingFields);

            accesoryDto.setHypoallergenic(getFieldError(node,"hypoallergenic",missingFields,Boolean.class));
            accesoryDto.setAccesoryType(AccesoryType.fromValue(getFieldError(node,"accesory_type",missingFields,String.class)));


            if (!missingFields.isEmpty()){
                throw new IllegalArgumentException("Missing fields: " + missingFields);
            }
            return accesoryDto;

        } else if ("hygiene".equals(type)) {
            HygieneDto hygieneDto = new HygieneDto();
            setCommomFields(hygieneDto, node,missingFields);

            hygieneDto.setVolume(getFieldError(node,"volume",missingFields,Double.class));
            hygieneDto.setHygieneType(HygieneType.fromValue(getFieldError(node,"hygiene_type",missingFields,String.class)));

            if (!missingFields.isEmpty()){
                throw new IllegalArgumentException("Missing fields: " + missingFields);
            }
            return hygieneDto;
        } else {
            throw new IllegalArgumentException("Type of product is not recognized");
        }
    }

    private void setCommomFields(ProductDto productDto,JsonNode node,Set<String> missingFields) {
        productDto.setCodeByBranch(getFieldError(node,"code_branch",missingFields,String.class));
        productDto.setName(getFieldError(node,"name",missingFields,String.class));
        productDto.setPrice(getFieldError(node,"price",missingFields,BigDecimal.class));
        productDto.setStock(getFieldError(node,"stock",missingFields,Integer.class));
        productDto.setDescription(getFieldError(node,"description",missingFields,String.class));
        productDto.setPetType(PetType.fromValue(getFieldError(node,"pet_type",missingFields,String.class)));
        productDto.setImageProduct(getFieldError(node,"image_product",missingFields,String.class));

        JsonNode categoryNode = node.get("category");
        if (categoryNode == null) {
            missingFields.add("category");
        }else {
            productDto.setCategory(getCategoryDto(categoryNode,missingFields));
        }

        JsonNode brandNode = node.get("brand");
        if (brandNode == null) {
            missingFields.add("brand");
        }else{
            productDto.setBrand(getBrandDto(brandNode,missingFields));
        }

    }

    private CategoryDto getCategoryDto(JsonNode node,Set<String> missingFields) {
        if (node==null || node.isNull()){
            missingFields.add("category");
            return null;
        }

        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setIdCategory(getFieldError(node,"id",missingFields, Long.class));
        return categoryDto;
    }

    private BrandDto getBrandDto(JsonNode node,Set<String> missingFields) {
        if (node==null || node.isNull()){
            missingFields.add("brand");
            return null;
        }
        BrandDto brandDto = new BrandDto();
        brandDto.setIdBrand(getFieldError(node,"id",missingFields, Long.class));
        return brandDto;
    }

    private <T> T getFieldError(JsonNode node,String fieldName, Set<String> missingFields,Class<T> fieldType) {
        JsonNode fieldNode = node.get(fieldName);

        if (fieldNode == null || fieldNode.isNull()){
            missingFields.add(fieldName);
            return null;
        }

        try {
            if (fieldType==String.class){
                return fieldType.cast(fieldNode.asText());
            } else if (fieldType==Double.class) {
                return fieldType.cast(fieldNode.asDouble());
            }else if (fieldType==Integer.class){
                return fieldType.cast(fieldNode.asInt());
            } else if (fieldType== BigDecimal.class) {
                return fieldType.cast(fieldNode.decimalValue());
            } else if (fieldType==Boolean.class) {
                return fieldType.cast(fieldNode.asBoolean());
            } else if (fieldType==Long.class) {
                return fieldType.cast(fieldNode.asLong());
            }else {
                throw new IllegalArgumentException("Unsupported field type: " + fieldType.getSimpleName());
            }
        } catch (Exception e) {
            missingFields.add(fieldName+" invalid type");
            return null;
        }


    }


}
