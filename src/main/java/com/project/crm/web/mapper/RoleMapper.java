package com.project.crm.web.mapper;

import com.project.crm.persistence.model.Prospects;
import com.project.crm.persistence.model.Role;
import com.project.crm.web.dto.ProspectCreateDto;
import com.project.crm.web.dto.ProspectDto;
import com.project.crm.web.dto.RoleCreateDto;
import com.project.crm.web.dto.RoleDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
@Mapper(componentModel = "spring", uses = {})
public interface RoleMapper {
    RoleMapper MAPPER = Mappers.getMapper(RoleMapper.class);
    RoleDto toRoleDto(Role role);
    Role toRole(RoleCreateDto roleCreateDto);
}
