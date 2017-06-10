package com.routeadvisor.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.routeadvisor.app.domain.TripDay;

import com.routeadvisor.app.repository.TripDayRepository;
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
 * REST controller for managing TripDay.
 */
@RestController
@RequestMapping("/api")
public class TripDayResource {

    private final Logger log = LoggerFactory.getLogger(TripDayResource.class);

    private static final String ENTITY_NAME = "tripDay";
        
    private final TripDayRepository tripDayRepository;

    public TripDayResource(TripDayRepository tripDayRepository) {
        this.tripDayRepository = tripDayRepository;
    }

    /**
     * POST  /trip-days : Create a new tripDay.
     *
     * @param tripDay the tripDay to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tripDay, or with status 400 (Bad Request) if the tripDay has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/trip-days")
    @Timed
    public ResponseEntity<TripDay> createTripDay(@Valid @RequestBody TripDay tripDay) throws URISyntaxException {
        log.debug("REST request to save TripDay : {}", tripDay);
        if (tripDay.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new tripDay cannot already have an ID")).body(null);
        }
        TripDay result = tripDayRepository.save(tripDay);
        return ResponseEntity.created(new URI("/api/trip-days/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /trip-days : Updates an existing tripDay.
     *
     * @param tripDay the tripDay to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tripDay,
     * or with status 400 (Bad Request) if the tripDay is not valid,
     * or with status 500 (Internal Server Error) if the tripDay couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/trip-days")
    @Timed
    public ResponseEntity<TripDay> updateTripDay(@Valid @RequestBody TripDay tripDay) throws URISyntaxException {
        log.debug("REST request to update TripDay : {}", tripDay);
        if (tripDay.getId() == null) {
            return createTripDay(tripDay);
        }
        TripDay result = tripDayRepository.save(tripDay);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tripDay.getId().toString()))
            .body(result);
    }

    /**
     * GET  /trip-days : get all the tripDays.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of tripDays in body
     */
    @GetMapping("/trip-days")
    @Timed
    public List<TripDay> getAllTripDays() {
        log.debug("REST request to get all TripDays");
        List<TripDay> tripDays = tripDayRepository.findAll();
        return tripDays;
    }

    /**
     * GET  /trip-days/:id : get the "id" tripDay.
     *
     * @param id the id of the tripDay to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tripDay, or with status 404 (Not Found)
     */
    @GetMapping("/trip-days/{id}")
    @Timed
    public ResponseEntity<TripDay> getTripDay(@PathVariable String id) {
        log.debug("REST request to get TripDay : {}", id);
        TripDay tripDay = tripDayRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(tripDay));
    }

    /**
     * DELETE  /trip-days/:id : delete the "id" tripDay.
     *
     * @param id the id of the tripDay to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/trip-days/{id}")
    @Timed
    public ResponseEntity<Void> deleteTripDay(@PathVariable String id) {
        log.debug("REST request to delete TripDay : {}", id);
        tripDayRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }

}
