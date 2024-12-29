package com.catdog.comerce.controller;

import com.catdog.comerce.dto.GenericResponse;
import com.catdog.comerce.dto.request.SellingDto;
import com.catdog.comerce.dto.request.SellingStateDto;
import com.catdog.comerce.dto.response.ResponseProductDto;
import com.catdog.comerce.dto.response.ResponseSelling;
import com.catdog.comerce.service.ISellingService;
import com.catdog.comerce.utils.MapperUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/selling")
public class SellingController {
    private final ISellingService sellingService;
    private final MapperUtil mapperUtil;

    //    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping
    public ResponseEntity<GenericResponse<ResponseSelling>> createSelling(@RequestBody @Valid SellingDto sellingDto) {
        return new ResponseEntity<>(new GenericResponse<>(201,"succes", Arrays.asList(sellingService.createSelling(sellingDto))), HttpStatus.CREATED);
    }

    @GetMapping
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<GenericResponse<ResponseSelling>> getAllSellings() {
        return new ResponseEntity<>(new GenericResponse<>(200,"success",sellingService.findAllSellings()),HttpStatus.OK);
    }

    @PatchMapping("/{id}")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<GenericResponse<ResponseSelling>> updateStateSelling(@RequestBody @Valid SellingStateDto sellingStateDto,@PathVariable Long id) {
        ResponseSelling responseSelling = sellingService.updateStateSelling(sellingStateDto, id);
        return new ResponseEntity<>(new GenericResponse<>(200,"success",Arrays.asList(responseSelling)), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenericResponse<ResponseSelling>> getSellingById(@PathVariable Long id) {
        ResponseSelling responseSelling = mapperUtil.map(sellingService.getById(id),ResponseSelling.class);
        return new ResponseEntity<>(new GenericResponse<>(200,"success", Arrays.asList(responseSelling)),HttpStatus.OK);
    }
}
