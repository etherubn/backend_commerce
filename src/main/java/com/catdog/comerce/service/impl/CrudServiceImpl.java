package com.catdog.comerce.service.impl;

import com.catdog.comerce.exception.NotFoundException;
import com.catdog.comerce.repository.RepoGeneric;
import com.catdog.comerce.service.ICrudService;
import com.catdog.comerce.utils.MapperUtil;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor

@AllArgsConstructor
abstract class CrudServiceImpl<Dto,T,ID> implements ICrudService<Dto,ID> {
    protected abstract RepoGeneric<T,ID> getRepo();
    protected abstract Class<T> getEntityClass();
    protected abstract Class<Dto> getDtoClass();
    protected abstract void setId(T entity,ID id);
    protected  MapperUtil mapperUtil;

    @Override
    public List<Dto> findAll() {
        getRepo().findAll().stream().forEach(food-> System.out.println(food));
        return mapperUtil.mapList(getRepo().findAll(),getDtoClass());
    }

    @Override
    public Dto create(Dto dto) {
        System.out.println(dto+" dto");
        T t = mapperUtil.map(dto,getEntityClass());
        System.out.println(t+ " entity");
        T savedEntity = getRepo().save(t);
        System.out.println(savedEntity+" saved");
        System.out.println(mapperUtil.map(savedEntity,getDtoClass())+" response");
        return mapperUtil.map(savedEntity,getDtoClass());
    }

    @Override
    public Dto update(Dto dto, ID id) {
        getRepo().findById(id).orElseThrow(()->new NotFoundException(getEntityClass().getSimpleName(), (Long) id));
        T entity = mapperUtil.map(dto,getEntityClass());
        setId(entity,id);
        T updatedEntity = getRepo().save(entity);


        return mapperUtil.map(updatedEntity,getDtoClass());
    }

    @Transactional
    @Override
    public void delete(ID id) {
        getRepo().findById(id).orElseThrow(()->new NotFoundException(getEntityClass().getSimpleName(), (Long) id));
        getRepo().deleteById(id);
    }

    @Override
    public Dto getById(ID id) {
        T entity = getRepo().findById(id).orElseThrow(()->new NotFoundException(getEntityClass().getSimpleName(), (Long) id));
        return mapperUtil.map(entity,getDtoClass());
    }
}
