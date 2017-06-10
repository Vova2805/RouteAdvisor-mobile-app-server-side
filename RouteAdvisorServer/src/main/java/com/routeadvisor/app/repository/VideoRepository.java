package com.routeadvisor.app.repository;

import com.routeadvisor.app.domain.Audio;
import com.routeadvisor.app.domain.Video;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Spring Data MongoDB repository for the Video entity.
 */
@SuppressWarnings("unused")
public interface VideoRepository extends MongoRepository<Video,String> {

    List<Video> findByPlaceId(String placeId);

}
