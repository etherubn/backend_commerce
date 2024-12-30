package com.catdog.comerce.service;

import com.catdog.comerce.dto.request.ProductDto;
import com.catdog.comerce.dto.request.UpdateProductDto;
import com.catdog.comerce.dto.response.ResponseProductDto;
import com.catdog.comerce.entity.Product;

import java.util.List;
import java.util.Optional;

public interface IProductService extends ICrudService<ProductDto,Long>{
    List<ResponseProductDto> findAllProducts();
    ResponseProductDto updateProduct(UpdateProductDto updateProductDto, Long idProduct);
    List<Product> bestSellingProducts(Long value);
    ResponseProductDto findProductById(Long id);
}
