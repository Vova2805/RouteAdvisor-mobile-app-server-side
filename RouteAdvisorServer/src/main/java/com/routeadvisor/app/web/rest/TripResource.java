package com.routeadvisor.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.routeadvisor.app.domain.Trip;

import com.routeadvisor.app.repository.TripRepository;
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
 * REST controller for managing Trip.
 */
@RestController
@RequestMapping("/api")
public class TripResource {

    private final Logger log = LoggerFactory.getLogger(TripResource.class);

    private static final String ENTITY_NAME = "trip";
        
    private final TripRepository tripRepository;

    public TripResource(TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }

    /**
     * POST  /trips : Create a new trip.
     *
     * @param trip the trip to create
     * @return the ResponseEntity with status 201 (Created) and with body the new trip, or with status 400 (Bad Request) if the trip has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/trips")
    @Timed
    public ResponseEntity<Trip> createTrip(@Valid @RequestBody Trip trip) throws URISyntaxException {
        log.debug("REST request to save Trip : {}", trip);
        if (trip.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new trip cannot already have an ID")).body(null);
        }
        Trip result = tripRepository.save(trip);
        return ResponseEntity.created(new URI("/api/trips/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /trips : Updates an existing trip.
     *
     * @param trip the trip to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated trip,
     * or with status 400 (Bad Request) if the trip is not valid,
     * or with status 500 (Internal Server Error) if the trip couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/trips")
    @Timed
    public ResponseEntity<Trip> updateTrip(@Valid @RequestBody Trip trip) throws URISyntaxException {
        log.debug("REST request to update Trip : {}", trip);
        if (trip.getId() == null) {
            return createTrip(trip);
        }
        Trip result = tripRepository.save(trip);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, trip.getId().toString()))
            .body(result);
    }

    /**
     * GET  /trips : get all the trips.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of trips in body
     */
    @GetMapping("/trips")
    @Timed
    public List<Trip> getAllTrips() {
        log.debug("REST request to get all Trips");
        List<Trip> trips = tripRepository.findAll();
        return trips;
    }

    /**
     * GET  /trips/:id : get the "id" trip.
     *
     * @param id the id of the trip to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the trip, or with status 404 (Not Found)
     */
    @GetMapping("/trips/{id}")
    @Timed
    public ResponseEntity<Trip> getTrip(@PathVariable String id) {
        log.debug("REST request to get Trip : {}", id);
        Trip trip = tripRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(trip));
    }

    /**
     * DELETE  /trips/:id : delete the "id" trip.
     *
     * @param id the id of the trip to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/trips/{id}")
    @Timed
    public ResponseEntity<Void> deleteTrip(@PathVariable String id) {
        log.debug("REST request to delete Trip : {}", id);
        tripRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }

}
