package com.catdog.comerce.service;

import com.catdog.comerce.dto.request.FoodDto;
import com.catdog.comerce.dto.request.UpdateFoodDto;
import com.catdog.comerce.dto.response.ResponseFoodDto;

public interface IFoodService extends ICrudService<FoodDto,Long>{
    ResponseFoodDto updateFood(UpdateFoodDto updateFoodDto,Long foodId);
}
