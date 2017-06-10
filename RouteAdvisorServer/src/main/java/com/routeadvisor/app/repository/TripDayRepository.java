package com.routeadvisor.app.repository;

import com.routeadvisor.app.domain.TripDay;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Spring Data MongoDB repository for the TripDay entity.
 */
@SuppressWarnings("unused")
public interface TripDayRepository extends MongoRepository<TripDay, String> {

    @Transactional
    Long removeByTripId(String tripId);

    @Transactional
    List<TripDay> deleteByTripId(String tripId);

    List<TripDay> findByTripId(String tripId);
}
