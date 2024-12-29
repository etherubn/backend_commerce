package com.catdog.comerce.controller;

import com.catdog.comerce.dto.GenericResponse;
import com.catdog.comerce.dto.request.FoodDto;
import com.catdog.comerce.dto.request.UpdateFoodDto;
import com.catdog.comerce.dto.response.ResponseFoodDto;
import com.catdog.comerce.dto.response.ResponseFoodDto;
import com.catdog.comerce.service.IFoodService;
import com.catdog.comerce.utils.MapperUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RequestMapping("api/v1/product/food")
@RestController
@RequiredArgsConstructor
public class FoodController {
    private final IFoodService foodService;
    private final MapperUtil mapperUtil;

    @PostMapping
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<GenericResponse<ResponseFoodDto>> createFood(@RequestBody @Valid FoodDto foodDto) {
        System.out.println("hola");
        ResponseFoodDto responseFoodDto = mapperUtil.map(foodService.create(foodDto), ResponseFoodDto.class);
        return new ResponseEntity<>(new GenericResponse<>(201,"success", Arrays.asList(responseFoodDto)), HttpStatus.CREATED);
    }

    @PreAuthorize("permitAll()")
    @GetMapping
    public ResponseEntity<GenericResponse<ResponseFoodDto>> getAllFood() {
        List<ResponseFoodDto> responseFoodDtoList = mapperUtil.mapList(foodService.findAll(), ResponseFoodDto.class);
        return new ResponseEntity<>(new GenericResponse<>(200,"success", responseFoodDtoList), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<GenericResponse<ResponseFoodDto>> getFood(@PathVariable Long id) {
        ResponseFoodDto responseFoodDto = mapperUtil.map(foodService.getById(id), ResponseFoodDto.class);
        return new ResponseEntity<>(new GenericResponse<>(200,"success",Arrays.asList(responseFoodDto)),HttpStatus.OK);
    }

    @PutMapping("/{id}")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<GenericResponse<ResponseFoodDto>> updateFood(@PathVariable Long id, @RequestBody @Valid UpdateFoodDto updateFoodDto) {
        ResponseFoodDto responseFoodDto = mapperUtil.map(foodService.updateFood(updateFoodDto,id), ResponseFoodDto.class);
        return new ResponseEntity<>(new GenericResponse<>(200,"success", Arrays.asList(responseFoodDto)), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteFood(@PathVariable Long id) {
        foodService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
