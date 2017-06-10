package com.routeadvisor.app.service.impl;

import com.routeadvisor.app.domain.Trip;
import com.routeadvisor.app.repository.TripRepository;
import com.routeadvisor.app.service.TripService;
import com.routeadvisor.app.service.dto.TripDTO;
import com.routeadvisor.app.service.mapper.TripMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Trip.
 */
@Service
public class TripServiceImpl implements TripService {

    private final Logger log = LoggerFactory.getLogger(TripServiceImpl.class);

    private final TripRepository tripRepository;

    private final TripMapper tripMapper;

    public TripServiceImpl(TripRepository tripRepository, TripMapper tripMapper) {
        this.tripRepository = tripRepository;
        this.tripMapper = tripMapper;
    }

    /**
     * Save a trip.
     *
     * @param tripDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TripDTO save(TripDTO tripDTO) {
        log.debug("Request to save Trip : {}", tripDTO);
        Trip trip = tripMapper.toEntity(tripDTO);
        trip = tripRepository.save(trip);
        TripDTO result = tripMapper.toDto(trip);
        return result;
    }

    /**
     * Get all the trips.
     *
     * @return the list of entities
     */
    @Override
    public List<TripDTO> findAll() {
        log.debug("Request to get all Trips");
        List<TripDTO> result = tripRepository.findAll().stream()
            .map(tripMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     * Get one trip by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    public TripDTO findOne(String id) {
        log.debug("Request to get Trip : {}", id);
        Trip trip = tripRepository.findOne(id);
        TripDTO tripDTO = tripMapper.toDto(trip);
        return tripDTO;
    }

    /**
     * Delete the  trip by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Trip : {}", id);
        tripRepository.delete(id);
    }
}
