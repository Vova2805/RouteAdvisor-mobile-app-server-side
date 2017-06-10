package com.routeadvisor.app.service.mapper;

import com.routeadvisor.app.domain.Template;
import com.routeadvisor.app.service.dto.TemplateDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity Template and its DTO TemplateDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TemplateMapper extends EntityMapper<TemplateDTO, Template> {


    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */

}
