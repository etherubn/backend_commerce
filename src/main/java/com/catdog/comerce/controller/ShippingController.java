package com.catdog.comerce.controller;

import com.catdog.comerce.dto.GenericResponse;
import com.catdog.comerce.dto.request.ShippingDto;
import com.catdog.comerce.service.IShippingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RequestMapping("/api/v1/shipping")
@RestController
@RequiredArgsConstructor
public class ShippingController {

    private final IShippingService shippingService;

    @PreAuthorize("permitAll()")
    @GetMapping
    public ResponseEntity<GenericResponse<ShippingDto>> getShipping() {
        return new ResponseEntity<>(new GenericResponse<>(200,"succes", Arrays.asList(shippingService.getShipping())), HttpStatus.OK);
    }


    @PutMapping
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<GenericResponse<ShippingDto>> updateShipping(@RequestBody @Valid ShippingDto shippingDto) {
        return new ResponseEntity<>(new GenericResponse<>(200,"succes", Arrays.asList(shippingService.updateShipping(shippingDto))), HttpStatus.OK);
    }

}
