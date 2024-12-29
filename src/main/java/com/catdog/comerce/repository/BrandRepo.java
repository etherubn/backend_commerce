package com.catdog.comerce.repository;

import com.catdog.comerce.entity.Brand;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface BrandRepo extends RepoGeneric<Brand,Long> {
    Optional<Brand> findByName(String name);
}
