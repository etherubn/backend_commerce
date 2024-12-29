package com.catdog.comerce.controller;

import com.catdog.comerce.dto.GenericResponse;
import com.catdog.comerce.dto.request.RoleDto;
import com.catdog.comerce.dto.response.ResponseRoleDto;
import com.catdog.comerce.service.IRoleService;
import com.catdog.comerce.utils.MapperUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/role")
@RequiredArgsConstructor
public class RoleController {
    private final MapperUtil mapperUtil;
    private final IRoleService roleService;


    @GetMapping
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<GenericResponse<ResponseRoleDto>> findAllRoles(){
        List<ResponseRoleDto> responseRoleDtos = mapperUtil.mapList(roleService.findAll(), ResponseRoleDto.class);

        return new ResponseEntity<>(new GenericResponse<>(200,"success", responseRoleDtos), HttpStatus.OK);
    }

    @GetMapping("/{id}")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<GenericResponse<ResponseRoleDto>> findRole(@PathVariable Long id){
        ResponseRoleDto responseRoleDto = mapperUtil.map(roleService.getById(id), ResponseRoleDto.class);

        return new ResponseEntity<>(new GenericResponse<>(200,"success", Arrays.asList(responseRoleDto)), HttpStatus.OK);
    }

    @PostMapping
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<GenericResponse<ResponseRoleDto>> createRole(@RequestBody @Valid RoleDto roleDto){
        ResponseRoleDto responseRoleDto = mapperUtil.map(roleService.create(roleDto), ResponseRoleDto.class);

        return new ResponseEntity<>(new GenericResponse<>(201,"success",Arrays.asList(responseRoleDto)),HttpStatus.CREATED);
    }

}
