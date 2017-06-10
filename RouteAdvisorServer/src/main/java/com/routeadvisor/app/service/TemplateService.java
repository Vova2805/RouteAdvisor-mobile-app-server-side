package com.routeadvisor.app.service;

import com.routeadvisor.app.service.dto.TemplateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Template.
 */
public interface TemplateService {

    /**
     * Save a template.
     *
     * @param templateDTO the entity to save
     * @return the persisted entity
     */
    TemplateDTO save(TemplateDTO templateDTO);

    /**
     * Get all the templates.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<TemplateDTO> findAll(Pageable pageable);

    /**
     * Get the "id" template.
     *
     * @param id the id of the entity
     * @return the entity
     */
    TemplateDTO findOne(String id);

    /**
     * Delete the "id" template.
     *
     * @param id the id of the entity
     */
    void delete(String id);
}
