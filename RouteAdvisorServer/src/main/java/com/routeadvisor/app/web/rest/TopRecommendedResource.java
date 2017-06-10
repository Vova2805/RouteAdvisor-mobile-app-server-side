package com.routeadvisor.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.routeadvisor.app.domain.TopRecommended;

import com.routeadvisor.app.repository.TopRecommendedRepository;
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
 * REST controller for managing TopRecommended.
 */
@RestController
@RequestMapping("/api")
public class TopRecommendedResource {

    private final Logger log = LoggerFactory.getLogger(TopRecommendedResource.class);

    private static final String ENTITY_NAME = "topRecommended";
        
    private final TopRecommendedRepository topRecommendedRepository;

    public TopRecommendedResource(TopRecommendedRepository topRecommendedRepository) {
        this.topRecommendedRepository = topRecommendedRepository;
    }

    /**
     * POST  /top-recommendeds : Create a new topRecommended.
     *
     * @param topRecommended the topRecommended to create
     * @return the ResponseEntity with status 201 (Created) and with body the new topRecommended, or with status 400 (Bad Request) if the topRecommended has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/top-recommendeds")
    @Timed
    public ResponseEntity<TopRecommended> createTopRecommended(@Valid @RequestBody TopRecommended topRecommended) throws URISyntaxException {
        log.debug("REST request to save TopRecommended : {}", topRecommended);
        if (topRecommended.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new topRecommended cannot already have an ID")).body(null);
        }
        TopRecommended result = topRecommendedRepository.save(topRecommended);
        return ResponseEntity.created(new URI("/api/top-recommendeds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /top-recommendeds : Updates an existing topRecommended.
     *
     * @param topRecommended the topRecommended to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated topRecommended,
     * or with status 400 (Bad Request) if the topRecommended is not valid,
     * or with status 500 (Internal Server Error) if the topRecommended couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/top-recommendeds")
    @Timed
    public ResponseEntity<TopRecommended> updateTopRecommended(@Valid @RequestBody TopRecommended topRecommended) throws URISyntaxException {
        log.debug("REST request to update TopRecommended : {}", topRecommended);
        if (topRecommended.getId() == null) {
            return createTopRecommended(topRecommended);
        }
        TopRecommended result = topRecommendedRepository.save(topRecommended);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, topRecommended.getId().toString()))
            .body(result);
    }

    /**
     * GET  /top-recommendeds : get all the topRecommendeds.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of topRecommendeds in body
     */
    @GetMapping("/top-recommendeds")
    @Timed
    public List<TopRecommended> getAllTopRecommendeds() {
        log.debug("REST request to get all TopRecommendeds");
        List<TopRecommended> topRecommendeds = topRecommendedRepository.findAll();
        return topRecommendeds;
    }

    /**
     * GET  /top-recommendeds/:id : get the "id" topRecommended.
     *
     * @param id the id of the topRecommended to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the topRecommended, or with status 404 (Not Found)
     */
    @GetMapping("/top-recommendeds/{id}")
    @Timed
    public ResponseEntity<TopRecommended> getTopRecommended(@PathVariable String id) {
        log.debug("REST request to get TopRecommended : {}", id);
        TopRecommended topRecommended = topRecommendedRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(topRecommended));
    }

    /**
     * DELETE  /top-recommendeds/:id : delete the "id" topRecommended.
     *
     * @param id the id of the topRecommended to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/top-recommendeds/{id}")
    @Timed
    public ResponseEntity<Void> deleteTopRecommended(@PathVariable String id) {
        log.debug("REST request to delete TopRecommended : {}", id);
        topRecommendedRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }

}
