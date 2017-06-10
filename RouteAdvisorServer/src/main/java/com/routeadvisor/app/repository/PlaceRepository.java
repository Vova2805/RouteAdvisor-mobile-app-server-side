package com.routeadvisor.app.repository;

import com.routeadvisor.app.domain.Place;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Place entity.
 */
@SuppressWarnings("unused")
public interface PlaceRepository extends MongoRepository<Place,String> {

}
