package com.routeadvisor.app.service;

import com.routeadvisor.app.service.dto.TripDTO;

import java.util.List;

/**
 * Service Interface for managing Trip.
 */
public interface TripService {

    /**
     * Save a trip.
     *
     * @param tripDTO the entity to save
     * @return the persisted entity
     */
    TripDTO save(TripDTO tripDTO);

    /**
     * Get all the trips.
     *
     * @return the list of entities
     */
    List<TripDTO> findAll();

    /**
     * Get the "id" trip.
     *
     * @param id the id of the entity
     * @return the entity
     */
    TripDTO findOne(String id);

    /**
     * Delete the "id" trip.
     *
     * @param id the id of the entity
     */
    void delete(String id);
}
