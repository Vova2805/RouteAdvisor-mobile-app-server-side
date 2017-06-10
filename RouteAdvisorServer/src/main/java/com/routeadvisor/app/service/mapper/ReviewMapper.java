package com.routeadvisor.app.service.mapper;

import com.routeadvisor.app.domain.Review;
import com.routeadvisor.app.service.dto.ReviewDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity Review and its DTO ReviewDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ReviewMapper extends EntityMapper<ReviewDTO, Review> {


    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */

}
