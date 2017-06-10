package com.routeadvisor.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.routeadvisor.app.domain.DayPlace;

import com.routeadvisor.app.repository.DayPlaceRepository;
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
 * REST controller for managing DayPlace.
 */
@RestController
@RequestMapping("/api")
public class DayPlaceResource {

    private final Logger log = LoggerFactory.getLogger(DayPlaceResource.class);

    private static final String ENTITY_NAME = "dayPlace";
        
    private final DayPlaceRepository dayPlaceRepository;

    public DayPlaceResource(DayPlaceRepository dayPlaceRepository) {
        this.dayPlaceRepository = dayPlaceRepository;
    }

    /**
     * POST  /day-places : Create a new dayPlace.
     *
     * @param dayPlace the dayPlace to create
     * @return the ResponseEntity with status 201 (Created) and with body the new dayPlace, or with status 400 (Bad Request) if the dayPlace has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/day-places")
    @Timed
    public ResponseEntity<DayPlace> createDayPlace(@Valid @RequestBody DayPlace dayPlace) throws URISyntaxException {
        log.debug("REST request to save DayPlace : {}", dayPlace);
        if (dayPlace.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new dayPlace cannot already have an ID")).body(null);
        }
        DayPlace result = dayPlaceRepository.save(dayPlace);
        return ResponseEntity.created(new URI("/api/day-places/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /day-places : Updates an existing dayPlace.
     *
     * @param dayPlace the dayPlace to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated dayPlace,
     * or with status 400 (Bad Request) if the dayPlace is not valid,
     * or with status 500 (Internal Server Error) if the dayPlace couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/day-places")
    @Timed
    public ResponseEntity<DayPlace> updateDayPlace(@Valid @RequestBody DayPlace dayPlace) throws URISyntaxException {
        log.debug("REST request to update DayPlace : {}", dayPlace);
        if (dayPlace.getId() == null) {
            return createDayPlace(dayPlace);
        }
        DayPlace result = dayPlaceRepository.save(dayPlace);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, dayPlace.getId().toString()))
            .body(result);
    }

    /**
     * GET  /day-places : get all the dayPlaces.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of dayPlaces in body
     */
    @GetMapping("/day-places")
    @Timed
    public List<DayPlace> getAllDayPlaces() {
        log.debug("REST request to get all DayPlaces");
        List<DayPlace> dayPlaces = dayPlaceRepository.findAll();
        return dayPlaces;
    }

    /**
     * GET  /day-places/:id : get the "id" dayPlace.
     *
     * @param id the id of the dayPlace to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the dayPlace, or with status 404 (Not Found)
     */
    @GetMapping("/day-places/{id}")
    @Timed
    public ResponseEntity<DayPlace> getDayPlace(@PathVariable String id) {
        log.debug("REST request to get DayPlace : {}", id);
        DayPlace dayPlace = dayPlaceRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(dayPlace));
    }

    /**
     * DELETE  /day-places/:id : delete the "id" dayPlace.
     *
     * @param id the id of the dayPlace to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/day-places/{id}")
    @Timed
    public ResponseEntity<Void> deleteDayPlace(@PathVariable String id) {
        log.debug("REST request to delete DayPlace : {}", id);
        dayPlaceRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }

}
