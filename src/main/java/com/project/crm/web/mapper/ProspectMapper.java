package com.project.crm.web.mapper;

import com.project.crm.persistence.model.Contact;
import com.project.crm.persistence.model.Prospects;
import com.project.crm.web.dto.ContactCreateDto;
import com.project.crm.web.dto.ProspectCreateDto;
import com.project.crm.web.dto.ProspectDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
@Mapper(componentModel = "spring", uses = {})
public interface ProspectMapper {
    ProspectMapper MAPPER = Mappers.getMapper(ProspectMapper.class);

     ProspectDto toProspectDto(Prospects prospects);
     Prospects toProspect(ProspectCreateDto prospectCreateDto);

}
