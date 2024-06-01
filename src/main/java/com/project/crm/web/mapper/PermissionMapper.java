package com.project.crm.web.mapper;

import com.project.crm.persistence.model.Permission;
import com.project.crm.web.dto.PermissionDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {})
public interface PermissionMapper {
    PermissionMapper MAPPER = Mappers.getMapper(PermissionMapper.class);

    default PermissionDto toPermissionDto(Permission  permission){
       return PermissionDto.builder()
               .id(permission.getId())
               .name(permission.getName())
               .roles(permission.getRoles().stream().map(RoleMapper.MAPPER::toRoleDto).collect(Collectors.toList()))
               .build();
    }

}
