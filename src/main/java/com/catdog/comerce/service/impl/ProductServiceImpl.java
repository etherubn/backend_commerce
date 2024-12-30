package com.catdog.comerce.service.impl;


import com.catdog.comerce.dto.request.*;
import com.catdog.comerce.dto.response.ResponseAccesoryDto;
import com.catdog.comerce.dto.response.ResponseFoodDto;
import com.catdog.comerce.dto.response.ResponseHygieneDto;
import com.catdog.comerce.dto.response.ResponseProductDto;
import com.catdog.comerce.entity.*;
import com.catdog.comerce.exception.NotFoundException;
import com.catdog.comerce.repository.*;
import com.catdog.comerce.service.IAccesoryService;
import com.catdog.comerce.service.IFoodService;
import com.catdog.comerce.service.IHygieneService;
import com.catdog.comerce.service.IProductService;
import com.catdog.comerce.utils.MapperUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
public class ProductServiceImpl extends CrudServiceImpl<ProductDto, Product,Long> implements IProductService {
    private final ProductRepo productRepo;
    private final IFoodService foodService;
    private final IAccesoryService accesoryService;
    private final IHygieneService hygieneService;

    public ProductServiceImpl(MapperUtil mapperUtil, ProductRepo productRepo, IFoodService foodService, IAccesoryService accesoryService, IHygieneService hygieneService) {
        super(mapperUtil);
        this.productRepo = productRepo;
        this.foodService = foodService;
        this.accesoryService = accesoryService;
        this.hygieneService = hygieneService;
    }

    @Override
    protected RepoGeneric<Product, Long> getRepo() {
        return productRepo;
    }

    @Override
    protected Class<Product> getEntityClass() {
        return Product.class;
    }

    @Override
    protected Class<ProductDto> getDtoClass() {
        return ProductDto.class;
    }

    @Override
    protected void setId(Product entity, Long idProduct) {
        entity.setIdProduct(idProduct);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResponseProductDto> findAllProducts() {
        return productRepo.findAll().stream()
                .map(product -> {
                    if (product instanceof Food){
                        return mapperUtil.map(product, ResponseFoodDto.class);
                    }else if (product instanceof Accesory){
                        return mapperUtil.map(product, ResponseAccesoryDto.class);
                    } else if (product instanceof Hygiene) {
                        return mapperUtil.map(product, ResponseHygieneDto.class);
                    }else {
                        throw new IllegalArgumentException("Product type not supported");
                    }
                }).toList();
    }

    @Override
    @Transactional
    public ProductDto create(ProductDto productDto) {
        if (productDto instanceof FoodDto){
            FoodDto foodDto = foodService.create((FoodDto) productDto);
            return foodDto;
        } else if (productDto instanceof AccesoryDto) {
            AccesoryDto accesoryDto = accesoryService.create((AccesoryDto) productDto);
            return accesoryDto;

        } else if (productDto instanceof HygieneDto) {
            HygieneDto hygieneDto = hygieneService.create((HygieneDto) productDto);
            return hygieneDto;
        }else {
            throw new IllegalArgumentException("Product type does not exist");
        }
    }

    @Override
    @Transactional
    public ResponseProductDto updateProduct(UpdateProductDto updateProductDto, Long idProduct) {
        if (updateProductDto instanceof UpdateFoodDto){
            return foodService.updateFood((UpdateFoodDto) updateProductDto, idProduct);
        }else if (updateProductDto instanceof UpdateAccesoryDto){
            return accesoryService.updateAccesory((UpdateAccesoryDto) updateProductDto,idProduct);
        } else if (updateProductDto instanceof UpdateHygieneDto) {
            return hygieneService.updateHygiene((UpdateHygieneDto) updateProductDto, idProduct);
        }else {
            throw new IllegalArgumentException("Product type does not exist");
        }
    }

    @Override
    public List<Product> bestSellingProducts(Long value) {
        if (value<=0) throw new IllegalArgumentException("value must be greater than 0");
        long adjustLimit = Math.min(value, productRepo.countAllProducts());
        return productRepo.bestSellingProducts(adjustLimit);
    }

    @Override
    public ResponseProductDto findProductById(Long id) {
        Product product = productRepo.findById(id).orElseThrow(()-> new NotFoundException("producto",id));

        if (product instanceof Food){
            return mapperUtil.map(product, ResponseFoodDto.class);
        }

        if (product instanceof Accesory){
            return mapperUtil.map(product, ResponseAccesoryDto.class);
        }

        if (product instanceof Hygiene){
            return mapperUtil.map(product, ResponseHygieneDto.class);
        }
        throw new IllegalArgumentException("Product type not supported");
    }


}
