package com.catdog.comerce.repository;

import com.catdog.comerce.entity.Accesory;
import org.springframework.stereotype.Repository;




@Repository
public interface AccesoryRepo extends RepoGeneric<Accesory,Long> {
    boolean existsByCodeByBranchAndBrand_Name(String code, String branch);
}
