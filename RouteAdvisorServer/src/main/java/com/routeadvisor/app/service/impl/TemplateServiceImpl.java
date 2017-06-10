package com.routeadvisor.app.service.impl;

import com.routeadvisor.app.domain.Template;
import com.routeadvisor.app.repository.TemplateRepository;
import com.routeadvisor.app.service.TemplateService;
import com.routeadvisor.app.service.dto.TemplateDTO;
import com.routeadvisor.app.service.mapper.TemplateMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing Template.
 */
@Service
public class TemplateServiceImpl implements TemplateService {

    private final Logger log = LoggerFactory.getLogger(TemplateServiceImpl.class);

    private final TemplateRepository templateRepository;

    private final TemplateMapper templateMapper;

    public TemplateServiceImpl(TemplateRepository templateRepository, TemplateMapper templateMapper) {
        this.templateRepository = templateRepository;
        this.templateMapper = templateMapper;
    }

    /**
     * Save a template.
     *
     * @param templateDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TemplateDTO save(TemplateDTO templateDTO) {
        log.debug("Request to save Template : {}", templateDTO);
        Template template = templateMapper.toEntity(templateDTO);
        template = templateRepository.save(template);
        TemplateDTO result = templateMapper.toDto(template);
        return result;
    }

    /**
     * Get all the templates.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    public Page<TemplateDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Templates");
        Page<Template> result = templateRepository.findAll(pageable);
        return result.map(template -> templateMapper.toDto(template));
    }

    /**
     * Get one template by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    public TemplateDTO findOne(String id) {
        log.debug("Request to get Template : {}", id);
        Template template = templateRepository.findOne(id);
        TemplateDTO templateDTO = templateMapper.toDto(template);
        return templateDTO;
    }

    /**
     * Delete the  template by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Template : {}", id);
        templateRepository.delete(id);
    }
}
