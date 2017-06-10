package com.routeadvisor.app.repository;

import com.routeadvisor.app.domain.Audio;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Spring Data MongoDB repository for the Audio entity.
 */
@SuppressWarnings("unused")
public interface AudioRepository extends MongoRepository<Audio, String> {

    List<Audio> findByPlaceId(String placeId);

}
