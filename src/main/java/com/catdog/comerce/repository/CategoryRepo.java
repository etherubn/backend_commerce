package com.catdog.comerce.repository;

import com.catdog.comerce.entity.Category;
import com.catdog.comerce.enums.CategoryType;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CategoryRepo extends RepoGeneric<Category,Long> {
    Optional<Category> findByType(CategoryType type);
}
