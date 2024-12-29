package com.catdog.comerce.repository;

import com.catdog.comerce.entity.Food;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface FoodRepo extends RepoGeneric<Food,Long> {
    boolean existsByCodeByBranchAndBrand_Name( String codeByBranch, String brandName);
 }
