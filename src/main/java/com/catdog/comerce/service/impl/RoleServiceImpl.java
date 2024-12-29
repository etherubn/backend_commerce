package com.catdog.comerce.service.impl;

import com.catdog.comerce.dto.request.RoleDto;
import com.catdog.comerce.entity.Role;
import com.catdog.comerce.exception.AlreadyExistsException;
import com.catdog.comerce.repository.RoleRepo;
import com.catdog.comerce.repository.RepoGeneric;
import com.catdog.comerce.service.IRoleService;
import com.catdog.comerce.utils.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class RoleServiceImpl extends CrudServiceImpl<RoleDto, Role,Long> implements IRoleService {
    private final RoleRepo roleRepo;

    public RoleServiceImpl(MapperUtil mapperUtil, RoleRepo roleRepo) {
        super(mapperUtil);
        this.roleRepo = roleRepo;
    }


    @Override
    protected RepoGeneric<Role, Long> getRepo() {
        return roleRepo;
    }

    @Override
    protected Class<Role> getEntityClass() {
        return Role.class;
    }

    @Override
    protected Class<RoleDto> getDtoClass() {
        return RoleDto.class;
    }

    @Override
    protected void setId(Role entity, Long aLong) {
        entity.setIdRole(aLong);
    }

    @Override
    public RoleDto create(RoleDto roleDto) {
        if (roleRepo.findByType(roleDto.getType()).isPresent()){
            throw new AlreadyExistsException(getEntityClass().getSimpleName(),roleDto.getType().getValue());
        }

        Role createdRole = roleRepo.save(mapperUtil.map(roleDto, Role.class));


        return mapperUtil.map(createdRole, RoleDto.class);
    }

    @Override
    public RoleDto update(RoleDto roleDto, Long aLong) {
        Optional<Role> optionalRole = roleRepo.findByType(roleDto.getType());

        if (optionalRole.isPresent() && !optionalRole.get().getIdRole().equals(aLong)){
            throw new AlreadyExistsException(getEntityClass().getSimpleName(),roleDto.getType().getValue());
        }

        Role role = mapperUtil.map(roleDto, Role.class);
        role.setIdRole(aLong);

        Role savedRole = roleRepo.save(role);
        return mapperUtil.map(savedRole, RoleDto.class);
    }
}
