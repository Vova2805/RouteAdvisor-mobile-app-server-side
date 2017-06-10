package com.routeadvisor.app.repository;

import com.routeadvisor.app.domain.Photo;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Spring Data MongoDB repository for the Photo entity.
 */
@SuppressWarnings("unused")
public interface PhotoRepository extends MongoRepository<Photo, String> {

    List<Photo> findByPlaceId(String placeId);

}
