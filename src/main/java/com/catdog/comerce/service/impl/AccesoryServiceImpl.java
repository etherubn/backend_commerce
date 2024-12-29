package com.catdog.comerce.service.impl;

import com.catdog.comerce.dto.request.AccesoryDto;
import com.catdog.comerce.dto.request.UpdateAccesoryDto;
import com.catdog.comerce.dto.response.ResponseAccesoryDto;
import com.catdog.comerce.dto.response.ResponseAccesoryDto;
import com.catdog.comerce.entity.*;
import com.catdog.comerce.exception.AlreadyExistsException;
import com.catdog.comerce.exception.NotFoundException;
import com.catdog.comerce.repository.*;
import com.catdog.comerce.service.IAccesoryService;
import com.catdog.comerce.utils.MapperUtil;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AccesoryServiceImpl extends CrudServiceImpl<AccesoryDto, Accesory,Long> implements IAccesoryService {

    private final AccesoryRepo accesoryRepo;
    private final CategoryRepo categoryRepo;
    private final BrandRepo brandRepo;
    private final ProductRepo productRepo;

    public AccesoryServiceImpl(MapperUtil mapperUtil, AccesoryRepo accesoryRepo, CategoryRepo categoryRepo, BrandRepo brandRepo, ProductRepo productRepo) {
        super(mapperUtil);
        this.accesoryRepo = accesoryRepo;
        this.categoryRepo = categoryRepo;
        this.brandRepo = brandRepo;
        this.productRepo = productRepo;
    }

    @Override
    protected RepoGeneric<Accesory, Long> getRepo() {
        return accesoryRepo;
    }

    @Override
    protected Class<Accesory> getEntityClass() {
        return Accesory.class;
    }

    @Override
    protected Class<AccesoryDto> getDtoClass() {
        return AccesoryDto.class;
    }

    @Override
    protected void setId(Accesory entity, Long accesoryId) {
        entity.setIdProduct(accesoryId);
    }

    @Override
    @Transactional
    public AccesoryDto create(AccesoryDto accesoryDto) {
        Brand brand = brandRepo.findById(accesoryDto.getBrand().getIdBrand())
                .orElseThrow(()-> new NotFoundException("brand",accesoryDto.getBrand().getIdBrand()));
        boolean accesoryExist  =accesoryRepo.existsByCodeByBranchAndBrand_Name(accesoryDto.getCodeByBranch(), brand.getName());

        if(accesoryExist){
            throw new AlreadyExistsException(getEntityClass().getSimpleName(),"code by branch:"+accesoryDto.getCodeByBranch()+" and "+brand.getName());
        }

        Accesory accesory= verifyAccesory(accesoryDto);

        return mapperUtil.map(accesoryRepo.save(accesory), AccesoryDto.class);
    }


    @Override
    @Transactional
    public ResponseAccesoryDto updateAccesory(UpdateAccesoryDto updateAccesoryDto, Long accesoryId) {
        Product product = productRepo.findById(accesoryId).orElseThrow(()-> new NotFoundException("accesory",accesoryId));
        if (!(product instanceof Accesory)) {
            throw new IllegalArgumentException("product is not accesory");
        }

        Accesory accesory = (Accesory) product;
        if (updateAccesoryDto.getBrand() != null) {
            Brand brand = brandRepo.findById(updateAccesoryDto.getBrand().getIdBrand())
                    .orElseThrow(()-> new NotFoundException("brand",updateAccesoryDto.getIdProduct()));

            if (updateAccesoryDto.getCodeByBranch()!=null|| !accesory.getBrand().equals(brand)) {
                boolean duplicate = productRepo.validateDuplicate(
                        updateAccesoryDto.getCodeByBranch()!=null?updateAccesoryDto.getCodeByBranch(): accesory.getCodeByBranch(),
                        updateAccesoryDto.getBrand().getIdBrand(),
                        accesoryId);
                if(duplicate) {
                    throw new AlreadyExistsException(getEntityClass().getSimpleName(), "code by branch:"+accesory.getCodeByBranch()+" and "+accesory.getBrand().getName());
                }

            }
            accesory.setBrand(brand);
        }
        if (updateAccesoryDto.getCategory() != null) {
            Category category = categoryRepo.findById(updateAccesoryDto.getCategory().getIdCategory())
                    .orElseThrow(()-> new NotFoundException("category",updateAccesoryDto.getCategory().getIdCategory()));

            accesory.setCategory(category);
        }

        if (updateAccesoryDto.getCodeByBranch()!=null && updateAccesoryDto.getBrand()==null){
            boolean duplicate = productRepo.validateDuplicate(
                    updateAccesoryDto.getCodeByBranch(),
                    accesory.getBrand().getIdBrand(),
                    accesoryId
            );
            if(duplicate) {
                throw new AlreadyExistsException(getEntityClass().getSimpleName(), "code by branch:"+accesory.getCodeByBranch()+" and "+accesory.getBrand().getName());
            }
            accesory.setCodeByBranch(updateAccesoryDto.getCodeByBranch());
        }

        Optional.ofNullable(updateAccesoryDto.getName()).ifPresent(e-> accesory.setName(e));
        Optional.ofNullable(updateAccesoryDto.getPrice()).ifPresent(e-> accesory.setPrice(e));
        Optional.ofNullable(updateAccesoryDto.getStock()).ifPresent(e-> accesory.setStock(e));
        Optional.ofNullable(updateAccesoryDto.getDescription()).ifPresent(e-> accesory.setDescription(e));
        Optional.ofNullable(updateAccesoryDto.getPetType()).ifPresent(e-> accesory.setPetType(e));
        Optional.ofNullable(updateAccesoryDto.getImageProduct()).ifPresent(e-> accesory.setImageProduct(e));
        Optional.ofNullable(updateAccesoryDto.getAccesoryType()).ifPresent(e-> accesory.setAccesoryType(e));
        Optional.ofNullable(updateAccesoryDto.getHypoallergenic()).ifPresent(e-> accesory.setHypoallergenic(e));
        return mapperUtil.map(accesoryRepo.save(accesory), ResponseAccesoryDto.class);
    }

    private Accesory verifyAccesory(AccesoryDto accesoryDto) {
        Category category = categoryRepo.findById(accesoryDto.getCategory().getIdCategory())
                .orElseThrow(()-> new NotFoundException("category",accesoryDto.getCategory().getIdCategory()));
        Brand brand = brandRepo.findById(accesoryDto.getBrand().getIdBrand())
                .orElseThrow(()-> new NotFoundException("brand",accesoryDto.getBrand().getIdBrand()));

        Accesory accesory = mapperUtil.map(accesoryDto, Accesory.class);
        accesory.setCategory(category);
        accesory.setBrand(brand);
        return accesory;
    }
}
