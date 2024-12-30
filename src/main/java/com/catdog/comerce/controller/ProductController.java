package com.catdog.comerce.controller;

import com.catdog.comerce.dto.GenericResponse;
import com.catdog.comerce.dto.request.*;
import com.catdog.comerce.dto.response.ResponseAccesoryDto;
import com.catdog.comerce.dto.response.ResponseFoodDto;
import com.catdog.comerce.dto.response.ResponseHygieneDto;
import com.catdog.comerce.dto.response.ResponseProductDto;
import com.catdog.comerce.service.IProductService;
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
@RequiredArgsConstructor
@RequestMapping("api/v1/product")
public class ProductController {

    private final IProductService productService;
    private final MapperUtil mapperUtil;

    @GetMapping
    @PreAuthorize("permitAll()")
    public ResponseEntity<GenericResponse<ResponseProductDto>> getProducts() {
        List<ResponseProductDto> responseProductDtoList = productService.findAllProducts();
        GenericResponse<ResponseProductDto> genericResponse = new GenericResponse<>(200,"success",responseProductDtoList);
        return new ResponseEntity<>(genericResponse, HttpStatus.OK);
    }

    @PostMapping
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseProductDto createProduct(@Valid @RequestBody ProductDto productDto) {
        ProductDto productDto1 = productService.create(productDto);
        if (productDto1 instanceof FoodDto) {
            ResponseFoodDto responseFoodDto = mapperUtil.map(productDto1, ResponseFoodDto.class);
            return responseFoodDto;
        } else if (productDto instanceof HygieneDto) {
            ResponseHygieneDto responseHygieneDto = mapperUtil.map(productDto1, ResponseHygieneDto.class);
            return responseHygieneDto;
        }else if (productDto instanceof AccesoryDto) {
            ResponseAccesoryDto responseAccesoryDto = mapperUtil.map(productDto1, ResponseAccesoryDto.class);
            return responseAccesoryDto;
        }else {
            throw new IllegalArgumentException("Product type does not exist");
        }
    }

    @PutMapping("/{id}")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseProductDto updateProduct(@PathVariable Long id, @Valid @RequestBody UpdateProductDto updateProductDto) {
        ResponseProductDto responseProductDto = productService.updateProduct(updateProductDto,id);
        return responseProductDto;
    }
    @DeleteMapping("/{id}")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenericResponse<ResponseProductDto>> getProductById(@PathVariable Long id) {

        return new ResponseEntity<>(new GenericResponse<>(200,"success", Arrays.asList(productService.findProductById(id))),HttpStatus.OK);
    }

}
