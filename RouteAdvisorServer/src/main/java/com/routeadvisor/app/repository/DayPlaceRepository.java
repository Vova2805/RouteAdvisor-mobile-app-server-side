package com.routeadvisor.app.repository;

import com.routeadvisor.app.domain.DayPlace;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Spring Data MongoDB repository for the DayPlace entity.
 */
@SuppressWarnings("unused")
public interface DayPlaceRepository extends MongoRepository<DayPlace, String> {

    List<DayPlace> findByTemplateDayId(String templateDayId);

    List<DayPlace> findByTripDayId(String tripDayId);

    Long removeByTripDayId(String tripDayId);

    Long removeByTripDayIdAndPlaceId(String tripDayId, String placeId);
}
