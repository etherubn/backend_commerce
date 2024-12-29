package com.catdog.comerce.service.impl;

import com.catdog.comerce.dto.request.BrandDto;
import com.catdog.comerce.entity.Brand;
import com.catdog.comerce.exception.AlreadyExistsException;
import com.catdog.comerce.exception.NotFoundException;
import com.catdog.comerce.repository.BrandRepo;
import com.catdog.comerce.repository.RepoGeneric;
import com.catdog.comerce.service.IBrandService;
import com.catdog.comerce.utils.MapperUtil;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BrandServiceImpl extends CrudServiceImpl<BrandDto, Brand,Long> implements IBrandService {
    private final BrandRepo brandRepo;


    public BrandServiceImpl(MapperUtil mapperUtil, BrandRepo brandRepo) {
        super(mapperUtil);
        this.brandRepo = brandRepo;
    }


    @Override
    protected RepoGeneric<Brand, Long> getRepo() {
        return brandRepo;
    }

    @Override
    protected Class<Brand> getEntityClass() {
        return Brand.class;
    }

    @Override
    protected Class<BrandDto> getDtoClass() {
        return BrandDto.class;
    }

    @Override
    protected void setId(Brand entity, Long aLong) {
        entity.setIdBrand(aLong);
    }

    @Override
    public BrandDto create(BrandDto brandDto) {
        if (brandRepo.findByName(brandDto.getName()).isPresent()){
            throw new AlreadyExistsException(getEntityClass().getSimpleName(),brandDto.getName());
        }

        Brand createdBrand = brandRepo.save(mapperUtil.map(brandDto, Brand.class));
        return mapperUtil.map(createdBrand, BrandDto.class);
    }

    @Override
    public BrandDto update(BrandDto brandDto, Long aLong) {
        brandRepo.findById(aLong).orElseThrow(()-> new NotFoundException("brand",aLong));
        Optional<Brand> optionalBrand = brandRepo.findByName(brandDto.getName());

        if (optionalBrand.isPresent() && !optionalBrand.get().getIdBrand().equals(aLong)){
            throw new AlreadyExistsException(getEntityClass().getSimpleName(),brandDto.getName());
        }

        Brand brand = mapperUtil.map(brandDto, Brand.class);
        brand.setIdBrand(aLong);

        Brand savedBrand = brandRepo.save(brand);
        return mapperUtil.map(savedBrand, BrandDto.class);
    }
}
