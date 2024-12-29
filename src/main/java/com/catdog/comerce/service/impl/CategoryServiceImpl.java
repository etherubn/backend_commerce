package com.catdog.comerce.service.impl;

import com.catdog.comerce.dto.request.CategoryDto;
import com.catdog.comerce.entity.Category;
import com.catdog.comerce.exception.AlreadyExistsException;
import com.catdog.comerce.exception.NotFoundException;
import com.catdog.comerce.repository.CategoryRepo;
import com.catdog.comerce.repository.RepoGeneric;
import com.catdog.comerce.service.ICategoryService;
import com.catdog.comerce.utils.MapperUtil;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class CategoryServiceImpl extends CrudServiceImpl<CategoryDto, Category,Long> implements ICategoryService {
    private final CategoryRepo categoryRepo;

    public CategoryServiceImpl(MapperUtil mapperUtil,CategoryRepo categoryRepo) {
        super(mapperUtil);
        this.categoryRepo=categoryRepo;
    }

    @Override
    protected RepoGeneric<Category, Long> getRepo() {
        return categoryRepo;
    }

    @Override
    protected Class<Category> getEntityClass() {
        return Category.class;
    }

    @Override
    protected Class<CategoryDto> getDtoClass() {
        return CategoryDto.class;
    }

    @Override
    protected void setId(Category entity, Long idCategory) {
        entity.setIdCategory(idCategory);
    }

    @Override
    public CategoryDto create(CategoryDto categoryDto) {
        if (categoryRepo.findByType(categoryDto.getType()).isPresent()){
            throw new AlreadyExistsException(getEntityClass().getSimpleName(),categoryDto.getType().getValue());
        }

        Category createdCategory = categoryRepo.save(mapperUtil.map(categoryDto, Category.class));
        return mapperUtil.map(createdCategory, CategoryDto.class);
    }

    @Override
    public CategoryDto update(CategoryDto categoryDto, Long idCategory) {
        categoryRepo.findById(idCategory).orElseThrow(()-> new NotFoundException("category",idCategory));

        Optional<Category> optionalCategory = categoryRepo.findByType(categoryDto.getType());

        if (optionalCategory.isPresent() && !optionalCategory.get().getIdCategory().equals(idCategory)){
            throw new AlreadyExistsException(getEntityClass().getSimpleName(),categoryDto.getType().getValue());
        }

        Category category = mapperUtil.map(categoryDto, Category.class);
        category.setIdCategory(idCategory);

        Category savedCategory = categoryRepo.save(category);
        return mapperUtil.map(savedCategory, CategoryDto.class);
    }
}
