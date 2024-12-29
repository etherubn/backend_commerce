package com.catdog.comerce.service;

import java.util.List;

public interface ICrudService<Dto,ID> {
    List<Dto> findAll();
    Dto create(Dto dto);
    Dto update(Dto dto,ID id);
    void delete(ID id);
    Dto getById(ID id);
}
