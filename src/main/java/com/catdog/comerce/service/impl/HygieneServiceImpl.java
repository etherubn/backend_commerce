package com.catdog.comerce.service.impl;

import com.catdog.comerce.dto.request.HygieneDto;
import com.catdog.comerce.dto.request.UpdateHygieneDto;
import com.catdog.comerce.dto.response.ResponseHygieneDto;
import com.catdog.comerce.entity.*;
import com.catdog.comerce.entity.Hygiene;
import com.catdog.comerce.exception.AlreadyExistsException;
import com.catdog.comerce.exception.NotFoundException;
import com.catdog.comerce.repository.*;
import com.catdog.comerce.service.IHygieneService;
import com.catdog.comerce.utils.MapperUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class HygieneServiceImpl extends CrudServiceImpl<HygieneDto, Hygiene,Long> implements IHygieneService {

    private final HygieneRepo hygieneRepo;
    private final CategoryRepo categoryRepo;
    private final BrandRepo brandRepo;
    private final ProductRepo productRepo;

    public HygieneServiceImpl(MapperUtil mapperUtil, HygieneRepo hygieneRepo, CategoryRepo categoryRepo, BrandRepo brandRepo, ProductRepo productRepo) {
        super(mapperUtil);
        this.hygieneRepo = hygieneRepo;
        this.categoryRepo = categoryRepo;
        this.brandRepo = brandRepo;
        this.productRepo = productRepo;
    }

    @Override
    protected RepoGeneric<Hygiene, Long> getRepo() {
        return hygieneRepo;
    }

    @Override
    protected Class<Hygiene> getEntityClass() {
        return Hygiene.class;
    }

    @Override
    protected Class<HygieneDto> getDtoClass() {
        return HygieneDto.class;
    }

    @Override
    protected void setId(Hygiene entity, Long aLong) {
        entity.setIdProduct(aLong);
    }

    @Override
    @Transactional
    public HygieneDto create(HygieneDto hygieneDto) {
        Brand brand = brandRepo.findById(hygieneDto.getBrand().getIdBrand())
                .orElseThrow(()-> new NotFoundException("brand",hygieneDto.getBrand().getIdBrand()));
        boolean hygieneExist  =hygieneRepo.existsByCodeByBranchAndBrand_Name(hygieneDto.getCodeByBranch(), brand.getName());
        if(hygieneExist){
            throw new AlreadyExistsException(getEntityClass().getSimpleName(),"code by branch:"+hygieneDto.getCodeByBranch()+" and "+brand.getName());
        }

        Hygiene hygiene=verifyHygiene(hygieneDto);

        return mapperUtil.map(hygieneRepo.save(hygiene), HygieneDto.class);
    }

    @Override
    @Transactional
    public ResponseHygieneDto updateHygiene(UpdateHygieneDto updateHygieneDto, Long hygieneId) {
        Product product = productRepo.findById(hygieneId).orElseThrow(()-> new NotFoundException("hygiene",hygieneId));
        if (!(product instanceof Hygiene)) {
            throw new IllegalArgumentException("product is not a Hygiene");
        }

        Hygiene hygiene = (Hygiene) product;

        if (updateHygieneDto.getBrand() != null) {
            Brand brand = brandRepo.findById(updateHygieneDto.getBrand().getIdBrand())
                    .orElseThrow(()-> new NotFoundException("brand",updateHygieneDto.getIdProduct()));

            if (updateHygieneDto.getCodeByBranch()!=null|| !hygiene.getBrand().equals(brand)) {
                boolean duplicate = productRepo.validateDuplicate(
                        updateHygieneDto.getCodeByBranch()!=null?updateHygieneDto.getCodeByBranch(): hygiene.getCodeByBranch(),
                        updateHygieneDto.getBrand().getIdBrand(),
                        hygieneId);
                if(duplicate) {
                    throw new AlreadyExistsException(getEntityClass().getSimpleName(),"code by branch:"+hygiene.getCodeByBranch()+" and "+hygiene.getBrand().getName());
                }

            }
            hygiene.setBrand(brand);
        }
        if (updateHygieneDto.getCategory() != null) {
            Category category = categoryRepo.findById(updateHygieneDto.getCategory().getIdCategory())
                    .orElseThrow(()-> new NotFoundException("category",updateHygieneDto.getCategory().getIdCategory()));

            hygiene.setCategory(category);
        }

        if (updateHygieneDto.getCodeByBranch()!=null && updateHygieneDto.getBrand()==null){
            boolean duplicate = productRepo.validateDuplicate(
                    updateHygieneDto.getCodeByBranch(),
                    hygiene.getBrand().getIdBrand(),
                    hygieneId
            );
            if(duplicate) {
                throw new AlreadyExistsException(getEntityClass().getSimpleName(),"code by branch:"+hygiene.getCodeByBranch()+" and "+hygiene.getBrand().getName());
            }
            hygiene.setCodeByBranch(updateHygieneDto.getCodeByBranch());
        }

        Optional.ofNullable(updateHygieneDto.getName()).ifPresent(e-> hygiene.setName(e));
        Optional.ofNullable(updateHygieneDto.getPrice()).ifPresent(e-> hygiene.setPrice(e));
        Optional.ofNullable(updateHygieneDto.getStock()).ifPresent(e-> hygiene.setStock(e));
        Optional.ofNullable(updateHygieneDto.getDescription()).ifPresent(e-> hygiene.setDescription(e));
        Optional.ofNullable(updateHygieneDto.getPetType()).ifPresent(e-> hygiene.setPetType(e));
        Optional.ofNullable(updateHygieneDto.getImageProduct()).ifPresent(e-> hygiene.setImageProduct(e));
        Optional.ofNullable(updateHygieneDto.getHygieneType()).ifPresent(e-> hygiene.setHygieneType(e));
        Optional.ofNullable(updateHygieneDto.getVolume()).ifPresent(e-> hygiene.setVolume(e));
        return mapperUtil.map(hygieneRepo.save(hygiene), ResponseHygieneDto.class);
    }

    private Hygiene verifyHygiene(HygieneDto hygieneDto) {
        Category category = categoryRepo.findById(hygieneDto.getCategory().getIdCategory())
                .orElseThrow(()-> new NotFoundException("category",hygieneDto.getCategory().getIdCategory()));
        Brand brand = brandRepo.findById(hygieneDto.getBrand().getIdBrand())
                .orElseThrow(()-> new NotFoundException("brand",hygieneDto.getBrand().getIdBrand()));

        Hygiene hygiene = mapperUtil.map(hygieneDto, Hygiene.class);
        hygiene.setCategory(category);
        hygiene.setBrand(brand);
        return hygiene;
    }
    
}
