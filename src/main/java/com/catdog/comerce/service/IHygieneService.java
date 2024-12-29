package com.catdog.comerce.service;

import com.catdog.comerce.dto.request.HygieneDto;
import com.catdog.comerce.dto.request.UpdateFoodDto;
import com.catdog.comerce.dto.request.UpdateHygieneDto;
import com.catdog.comerce.dto.response.ResponseHygieneDto;

public interface IHygieneService extends ICrudService<HygieneDto,Long>{
    ResponseHygieneDto updateHygiene(UpdateHygieneDto updateHygieneDto, Long hygieneId);
}
