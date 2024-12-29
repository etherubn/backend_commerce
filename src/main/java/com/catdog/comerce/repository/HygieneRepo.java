package com.catdog.comerce.repository;

import com.catdog.comerce.entity.Hygiene;

import org.springframework.stereotype.Repository;




@Repository
public interface HygieneRepo extends RepoGeneric<Hygiene,Long> {
    boolean existsByCodeByBranchAndBrand_Name(String code, String branch);
}
