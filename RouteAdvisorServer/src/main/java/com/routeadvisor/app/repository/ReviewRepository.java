package com.routeadvisor.app.repository;

import com.routeadvisor.app.domain.Review;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Spring Data MongoDB repository for the Review entity.
 */
@SuppressWarnings("unused")
public interface ReviewRepository extends MongoRepository<Review, String> {

    List<Review> findByPlaceId(String placeId);

    List<Review> findByTemplateId(String templateId);

}
