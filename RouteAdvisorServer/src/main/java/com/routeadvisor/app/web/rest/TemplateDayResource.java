package com.routeadvisor.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.routeadvisor.app.domain.TemplateDay;

import com.routeadvisor.app.repository.TemplateDayRepository;
import com.routeadvisor.app.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing TemplateDay.
 */
@RestController
@RequestMapping("/api")
public class TemplateDayResource {

    private final Logger log = LoggerFactory.getLogger(TemplateDayResource.class);

    private static final String ENTITY_NAME = "templateDay";
        
    private final TemplateDayRepository templateDayRepository;

    public TemplateDayResource(TemplateDayRepository templateDayRepository) {
        this.templateDayRepository = templateDayRepository;
    }

    /**
     * POST  /template-days : Create a new templateDay.
     *
     * @param templateDay the templateDay to create
     * @return the ResponseEntity with status 201 (Created) and with body the new templateDay, or with status 400 (Bad Request) if the templateDay has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/template-days")
    @Timed
    public ResponseEntity<TemplateDay> createTemplateDay(@Valid @RequestBody TemplateDay templateDay) throws URISyntaxException {
        log.debug("REST request to save TemplateDay : {}", templateDay);
        if (templateDay.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new templateDay cannot already have an ID")).body(null);
        }
        TemplateDay result = templateDayRepository.save(templateDay);
        return ResponseEntity.created(new URI("/api/template-days/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /template-days : Updates an existing templateDay.
     *
     * @param templateDay the templateDay to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated templateDay,
     * or with status 400 (Bad Request) if the templateDay is not valid,
     * or with status 500 (Internal Server Error) if the templateDay couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/template-days")
    @Timed
    public ResponseEntity<TemplateDay> updateTemplateDay(@Valid @RequestBody TemplateDay templateDay) throws URISyntaxException {
        log.debug("REST request to update TemplateDay : {}", templateDay);
        if (templateDay.getId() == null) {
            return createTemplateDay(templateDay);
        }
        TemplateDay result = templateDayRepository.save(templateDay);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, templateDay.getId().toString()))
            .body(result);
    }

    /**
     * GET  /template-days : get all the templateDays.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of templateDays in body
     */
    @GetMapping("/template-days")
    @Timed
    public List<TemplateDay> getAllTemplateDays() {
        log.debug("REST request to get all TemplateDays");
        List<TemplateDay> templateDays = templateDayRepository.findAll();
        return templateDays;
    }

    /**
     * GET  /template-days/:id : get the "id" templateDay.
     *
     * @param id the id of the templateDay to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the templateDay, or with status 404 (Not Found)
     */
    @GetMapping("/template-days/{id}")
    @Timed
    public ResponseEntity<TemplateDay> getTemplateDay(@PathVariable String id) {
        log.debug("REST request to get TemplateDay : {}", id);
        TemplateDay templateDay = templateDayRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(templateDay));
    }

    /**
     * DELETE  /template-days/:id : delete the "id" templateDay.
     *
     * @param id the id of the templateDay to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/template-days/{id}")
    @Timed
    public ResponseEntity<Void> deleteTemplateDay(@PathVariable String id) {
        log.debug("REST request to delete TemplateDay : {}", id);
        templateDayRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }

}
