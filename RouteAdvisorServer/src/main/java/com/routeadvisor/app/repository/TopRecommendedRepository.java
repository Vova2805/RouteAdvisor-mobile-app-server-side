package com.routeadvisor.app.repository;

import com.routeadvisor.app.domain.TopRecommended;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the TopRecommended entity.
 */
@SuppressWarnings("unused")
public interface TopRecommendedRepository extends MongoRepository<TopRecommended,String> {

}
