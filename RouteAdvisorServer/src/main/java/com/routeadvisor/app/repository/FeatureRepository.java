package com.routeadvisor.app.repository;

import com.routeadvisor.app.domain.Feature;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Feature entity.
 */
@SuppressWarnings("unused")
public interface FeatureRepository extends MongoRepository<Feature,String> {

}
