package com.catdog.comerce.service;

import com.catdog.comerce.dto.request.SellingDto;
import com.catdog.comerce.dto.request.SellingStateDto;
import com.catdog.comerce.dto.response.ResponseSelling;

import java.util.List;

public interface ISellingService extends ICrudService<SellingDto,Long>{
    ResponseSelling createSelling(SellingDto sellingDto);
    List<ResponseSelling> findAllSellings();
    ResponseSelling updateStateSelling(SellingStateDto sellingStateDto, Long id);
}
