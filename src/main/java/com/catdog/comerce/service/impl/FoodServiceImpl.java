package com.catdog.comerce.service.impl;

import com.catdog.comerce.dto.request.FoodDto;
import com.catdog.comerce.dto.request.UpdateFoodDto;
import com.catdog.comerce.dto.response.ResponseFoodDto;
import com.catdog.comerce.entity.Brand;
import com.catdog.comerce.entity.Category;
import com.catdog.comerce.entity.Food;
import com.catdog.comerce.entity.Product;
import com.catdog.comerce.exception.AlreadyExistsException;
import com.catdog.comerce.exception.NotFoundException;
import com.catdog.comerce.repository.*;
import com.catdog.comerce.service.IFoodService;
import com.catdog.comerce.utils.MapperUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
public class FoodServiceImpl extends CrudServiceImpl<FoodDto, Food,Long> implements IFoodService {
    
    private final FoodRepo foodRepo;
    private final CategoryRepo categoryRepo;
    private final BrandRepo brandRepo;
    private final ProductRepo productRepo;

    public FoodServiceImpl(MapperUtil mapperUtil, FoodRepo foodRepo, CategoryRepo categoryRepo, BrandRepo brandRepo, ProductRepo productRepo) {
        super(mapperUtil);
        this.foodRepo = foodRepo;
        this.categoryRepo = categoryRepo;
        this.brandRepo = brandRepo;
        this.productRepo = productRepo;
    }

    @Override
    protected RepoGeneric<Food, Long> getRepo() {
        return foodRepo;
    }

    @Override
    protected Class<Food> getEntityClass() {
        return Food.class;
    }

    @Override
    protected Class<FoodDto> getDtoClass() {
        return FoodDto.class;
    }

    @Override
    protected void setId(Food entity, Long foodId) {
        entity.setIdProduct(foodId);
    }

    @Override
    @Transactional
    public FoodDto create(FoodDto foodDto) {
        Brand brand = brandRepo.findById(foodDto.getBrand().getIdBrand())
                .orElseThrow(()-> new NotFoundException("brand",foodDto.getBrand().getIdBrand()));
        boolean foodExist  =foodRepo.existsByCodeByBranchAndBrand_Name(foodDto.getCodeByBranch(), brand.getName());
        if(foodExist){
            throw new AlreadyExistsException(getEntityClass().getSimpleName(),"code by branch:"+foodDto.getCodeByBranch()+" and "+brand.getName());
        }

        Food food=verifyFood(foodDto);

        return mapperUtil.map(foodRepo.save(food), FoodDto.class);
    }

    @Override
    @Transactional
    public ResponseFoodDto updateFood(UpdateFoodDto updateFoodDto,Long foodId) {
        Product product = productRepo.findById(foodId).orElseThrow(()-> new NotFoundException("food",foodId));
        if (!(product instanceof Food)){
            throw new IllegalArgumentException("product is not a food");
        }
        Food food = (Food) product;

        System.out.println(food);
        if (updateFoodDto.getBrand() != null) {
            Brand brand = brandRepo.findById(updateFoodDto.getBrand().getIdBrand())
                    .orElseThrow(()-> new NotFoundException("brand",updateFoodDto.getIdProduct()));

            if (updateFoodDto.getCodeByBranch()!=null|| !food.getBrand().equals(brand)) {
                boolean duplicate = productRepo.validateDuplicate(
                        updateFoodDto.getCodeByBranch()!=null?updateFoodDto.getCodeByBranch(): food.getCodeByBranch(),
                        updateFoodDto.getBrand().getIdBrand(),
                        foodId);
                if(duplicate) {
                    throw new AlreadyExistsException(getEntityClass().getSimpleName(), "code by branch:"+food.getCodeByBranch()+" and "+food.getBrand().getName());
                }

            }
            food.setBrand(brand);
        }
        if (updateFoodDto.getCategory() != null) {
            Category category = categoryRepo.findById(updateFoodDto.getCategory().getIdCategory())
                    .orElseThrow(()-> new NotFoundException("category",updateFoodDto.getCategory().getIdCategory()));

            food.setCategory(category);
        }

        if (updateFoodDto.getCodeByBranch()!=null && updateFoodDto.getBrand()==null){
            boolean duplicate = productRepo.validateDuplicate(
                    updateFoodDto.getCodeByBranch(),
                    food.getBrand().getIdBrand(),
                    foodId
            );
            if(duplicate) {
                throw new AlreadyExistsException(getEntityClass().getSimpleName(),"code by branch:"+food.getCodeByBranch()+" and "+food.getBrand().getName());
            }
            food.setCodeByBranch(updateFoodDto.getCodeByBranch());
        }

        Optional.ofNullable(updateFoodDto.getName()).ifPresent(e-> food.setName(e));
        Optional.ofNullable(updateFoodDto.getPrice()).ifPresent(e-> food.setPrice(e));
        Optional.ofNullable(updateFoodDto.getStock()).ifPresent(e-> food.setStock(e));
        Optional.ofNullable(updateFoodDto.getDescription()).ifPresent(e-> food.setDescription(e));
        Optional.ofNullable(updateFoodDto.getPetType()).ifPresent(e-> food.setPetType(e));
        Optional.ofNullable(updateFoodDto.getImageProduct()).ifPresent(e-> food.setImageProduct(e));
        Optional.ofNullable(updateFoodDto.getRefrigeration()).ifPresent(e-> food.setRefrigeration(e));
        Optional.ofNullable(updateFoodDto.getWeight()).ifPresent(e-> food.setWeight(e));
        Optional.ofNullable(updateFoodDto.getFoodType()).ifPresent(e-> food.setFoodType(e));
        return mapperUtil.map(foodRepo.save(food), ResponseFoodDto.class);
    }

    private Food verifyFood(FoodDto foodDto) {
        Category category = categoryRepo.findById(foodDto.getCategory().getIdCategory())
                .orElseThrow(()-> new NotFoundException("category",foodDto.getCategory().getIdCategory()));
        Brand brand = brandRepo.findById(foodDto.getBrand().getIdBrand())
                .orElseThrow(()-> new NotFoundException("brand",foodDto.getBrand().getIdBrand()));

        Food food = mapperUtil.map(foodDto, Food.class);
        food.setCategory(category);
        food.setBrand(brand);
        return food;
    }



}
