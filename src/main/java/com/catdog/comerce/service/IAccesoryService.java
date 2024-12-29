package com.catdog.comerce.service;

import com.catdog.comerce.dto.request.AccesoryDto;
import com.catdog.comerce.dto.request.UpdateAccesoryDto;
import com.catdog.comerce.dto.request.UpdateFoodDto;
import com.catdog.comerce.dto.response.ResponseAccesoryDto;
import com.catdog.comerce.dto.response.ResponseFoodDto;

public interface IAccesoryService extends ICrudService<AccesoryDto,Long>{
    ResponseAccesoryDto updateAccesory(UpdateAccesoryDto updateFoodDto, Long accesoryId);
}
