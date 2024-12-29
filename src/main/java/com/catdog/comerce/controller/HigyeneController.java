package com.catdog.comerce.controller;

import com.catdog.comerce.dto.GenericResponse;
import com.catdog.comerce.dto.request.HygieneDto;
import com.catdog.comerce.dto.request.UpdateHygieneDto;
import com.catdog.comerce.dto.response.ResponseAccesoryDto;
import com.catdog.comerce.dto.response.ResponseHygieneDto;
import com.catdog.comerce.service.IHygieneService;
import com.catdog.comerce.utils.MapperUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RequestMapping("api/v1/product/hygiene")
@RestController
@RequiredArgsConstructor
public class HigyeneController {
    private final IHygieneService hygieneService;
    private final MapperUtil mapperUtil;

    @PostMapping
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<GenericResponse<ResponseHygieneDto>> createHygiene(@RequestBody @Valid HygieneDto hygieneDto) {
        System.out.println("hola");
        ResponseHygieneDto responseHygieneDto = mapperUtil.map(hygieneService.create(hygieneDto), ResponseHygieneDto.class);
        return new ResponseEntity<>(new GenericResponse<>(201,"success", Arrays.asList(responseHygieneDto)), HttpStatus.CREATED);
    }

    @PreAuthorize("permitAll()")
    @GetMapping
    public ResponseEntity<GenericResponse<ResponseHygieneDto>> getAllHygiene() {
        List<ResponseHygieneDto> responseHygieneDtoList = mapperUtil.mapList(hygieneService.findAll(), ResponseHygieneDto.class);
        return new ResponseEntity<>(new GenericResponse<>(200,"success", responseHygieneDtoList), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<GenericResponse<ResponseAccesoryDto>> getAccesory(@PathVariable Long id) {
        ResponseAccesoryDto responseAccesoryDto = mapperUtil.map(hygieneService.getById(id), ResponseAccesoryDto.class);
        return new ResponseEntity<>(new GenericResponse<>(200,"success",Arrays.asList(responseAccesoryDto)),HttpStatus.OK);
    }

    @PutMapping("/{id}")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<GenericResponse<ResponseHygieneDto>> updateHygiene(@PathVariable Long id, @RequestBody @Valid UpdateHygieneDto updateHygieneDto) {
        ResponseHygieneDto responseHygieneDto = mapperUtil.map(hygieneService.updateHygiene(updateHygieneDto,id), ResponseHygieneDto.class);
        return new ResponseEntity<>(new GenericResponse<>(200,"success", Arrays.asList(responseHygieneDto)), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteHygiene(@PathVariable Long id) {
        hygieneService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
