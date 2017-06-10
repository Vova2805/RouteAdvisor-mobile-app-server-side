package com.routeadvisor.app.repository;

import com.routeadvisor.app.domain.Trip;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Trip entity.
 */
@SuppressWarnings("unused")
public interface TripRepository extends MongoRepository<Trip,String> {

    Long removeByTrash(Boolean trash);
}
