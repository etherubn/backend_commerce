package com.catdog.comerce.controller;

import com.catdog.comerce.dto.GenericResponse;
import com.catdog.comerce.dto.request.AccesoryDto;
import com.catdog.comerce.dto.request.UpdateAccesoryDto;
import com.catdog.comerce.dto.response.ResponseAccesoryDto;
import com.catdog.comerce.service.IAccesoryService;
import com.catdog.comerce.utils.MapperUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RequestMapping("api/v1/product/accesory")
@RestController
@RequiredArgsConstructor
public class AccesoryController {
    private final IAccesoryService accesoryService;
    private final MapperUtil mapperUtil;

    @PostMapping
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<GenericResponse<ResponseAccesoryDto>> createAccesory(@RequestBody @Valid AccesoryDto accesoryDto) {
        System.out.println("hola");
        ResponseAccesoryDto responseAccesoryDto = mapperUtil.map(accesoryService.create(accesoryDto), ResponseAccesoryDto.class);
        return new ResponseEntity<>(new GenericResponse<>(201,"success", Arrays.asList(responseAccesoryDto)), HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("permitAll()")
    public ResponseEntity<GenericResponse<ResponseAccesoryDto>> getAllAccesory() {
        List<ResponseAccesoryDto> responseAccesoryDtos = mapperUtil.mapList(accesoryService.findAll(), ResponseAccesoryDto.class);
        return new ResponseEntity<>(new GenericResponse<>(200,"success",responseAccesoryDtos),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<GenericResponse<ResponseAccesoryDto>> getAccesory(@PathVariable Long id) {
        ResponseAccesoryDto responseAccesoryDto = mapperUtil.map(accesoryService.getById(id), ResponseAccesoryDto.class);
        return new ResponseEntity<>(new GenericResponse<>(200,"success",Arrays.asList(responseAccesoryDto)),HttpStatus.OK);
    }

    @PutMapping("/{id}")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<GenericResponse<ResponseAccesoryDto>> updateFood(@PathVariable Long id, @RequestBody @Valid UpdateAccesoryDto updateAccesoryDto) {
        ResponseAccesoryDto responseAccesoryDto = mapperUtil.map(accesoryService.updateAccesory(updateAccesoryDto,id), ResponseAccesoryDto.class);
        return new ResponseEntity<>(new GenericResponse<>(200,"success", Arrays.asList(responseAccesoryDto)), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteAccesory(@PathVariable Long id) {
        accesoryService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
