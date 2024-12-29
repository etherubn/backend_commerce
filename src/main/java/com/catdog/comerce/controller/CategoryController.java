package com.catdog.comerce.controller;

import com.catdog.comerce.dto.GenericResponse;
import com.catdog.comerce.dto.request.CategoryDto;
import com.catdog.comerce.dto.response.ResponseCategoryDto;
import com.catdog.comerce.service.ICategoryService;
import com.catdog.comerce.utils.MapperUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {
    private final MapperUtil mapperUtil;
    private final ICategoryService categoryService;


    @PreAuthorize("permitAll()")
    @GetMapping
    public ResponseEntity<GenericResponse<ResponseCategoryDto>> findAllCategorys(){
        List<ResponseCategoryDto> responseCategoryDtos = mapperUtil.mapList(categoryService.findAll(), ResponseCategoryDto.class);

        return new ResponseEntity<>(new GenericResponse<>(200,"success", responseCategoryDtos), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<GenericResponse<ResponseCategoryDto>> findCategory(@PathVariable Long id){
        ResponseCategoryDto responseCategoryDto = mapperUtil.map(categoryService.getById(id), ResponseCategoryDto.class);

        return new ResponseEntity<>(new GenericResponse<>(200,"success", Arrays.asList(responseCategoryDto)), HttpStatus.OK);
    }

    @PostMapping
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<GenericResponse<ResponseCategoryDto>> createCategory(@RequestBody @Valid CategoryDto categoryDto){
        ResponseCategoryDto responseCategoryDto = mapperUtil.map(categoryService.create(categoryDto), ResponseCategoryDto.class);

        return new ResponseEntity<>(new GenericResponse<>(201,"success",Arrays.asList(responseCategoryDto)),HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id){
        categoryService.delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<GenericResponse<ResponseCategoryDto>> updateCategory(@RequestBody @Valid CategoryDto categoryDto,@PathVariable Long id){
        ResponseCategoryDto responseCategoryDto = mapperUtil.map(categoryService.update(categoryDto,id), ResponseCategoryDto.class);
        return new ResponseEntity<>(new GenericResponse<ResponseCategoryDto>(200,"success",Arrays.asList(responseCategoryDto)),HttpStatus.OK);
    }
}
