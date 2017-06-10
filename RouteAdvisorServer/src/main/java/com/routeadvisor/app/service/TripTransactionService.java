package com.routeadvisor.app.service;

import com.routeadvisor.app.domain.*;
import com.routeadvisor.app.repository.*;
import com.routeadvisor.app.web.rest.vm.TripVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class TripTransactionService {
    private final Logger log = LoggerFactory.getLogger(TripTransactionService.class);

    @Autowired
    private PlaceRepository placeRepository;
    @Autowired
    private TemplateRepository templateRepository;
    @Autowired
    private TemplateDayRepository templateDayRepository;
    @Autowired
    private TripDayRepository tripDayRepository;
    @Autowired
    private DayPlaceRepository dayPlaceRepository;
    @Autowired
    private GoogleService googleService;
    @Autowired
    private TripRepository tripRepository;

    public Trip buildAndSaveTrip(TripVM tripVM) {
        Place place = placeRepository.findOne(tripVM.getCity().getId());
        Trip trip = new Trip();
        try {
            trip = trip
                .userId("1")
                .cityId(tripVM.getCity().getId())
                .name(tripVM.getName())
                .description(tripVM.getDescription())
                .photoURL(place.getPhotoURL())
                .startDate(tripVM.getStartDate() == null ? null : ZonedDateTime.parse(tripVM.getStartDate()))
                .endDate(tripVM.getEndDate() == null ? null : ZonedDateTime.parse(tripVM.getEndDate()))
                .cost(0f)
                .notesCount(0)
                .datesKnown(tripVM.getDatesKnown() == null ? false : tripVM.getDatesKnown())
                .accessible(false)
                .offline(false)
                .trash(false)
                .token(UUID.randomUUID().toString())
                .hours(0f)
                .length(0f);

            Place origin = null;
            Place destination = null;

            if (tripVM.getArrival() != null) {
                trip = trip.arrivalId(tripVM.getArrival().getId());
                origin = findPlaceOrNull(tripVM.getArrival().getId());
            }
            if (tripVM.getAccommodation() != null) {
                trip = trip.arrivalId(tripVM.getAccommodation().getId());
                destination = findPlaceOrNull(tripVM.getAccommodation().getId());
            }
            Template foundTemplate = null;
            if (tripVM.getTemplate() != null) {
                foundTemplate = templateRepository.findOne(tripVM.getTemplate().getId());
            }

            Float tripTime = 0f;
            Float tripLength = 0f;

            trip = tripRepository.save(trip);

            if (foundTemplate == null) {
                org.springframework.data.util.Pair<Float, Float> floatFloatPair =
                    googleService.calculateDurationDistance(origin, destination, new ArrayList<>());
                tripTime = floatFloatPair.getFirst();
                tripLength = floatFloatPair.getSecond();
            } else {
                ZonedDateTime startDate = trip.getStartDate();
                if (startDate == null) {
                    startDate = ZonedDateTime.now();
                }
                int dayIndex = 0;
                List<Place> placeList;
                List<TemplateDay> templateDays = templateDayRepository.findByTemplateId(foundTemplate.getId());
                for (TemplateDay templateDay : templateDays) {
                    if (destination == null) {
                        destination = findPlaceOrNull(templateDay.getDestinationId());
                    }
                    if (origin == null) {
                        origin = findPlaceOrNull(templateDay.getOriginId());
                    }
                    placeList = new ArrayList<>();
                    List<DayPlace> dayPlaces = dayPlaceRepository.findByTemplateDayId(templateDay.getId());
                    for (DayPlace dayPlace : dayPlaces) {
                        placeList.add(findPlaceOrNull(dayPlace.getPlaceId()));
                    }

                    placeList.removeIf(elem -> elem == null);
                    org.springframework.data.util.Pair<Float, Float> floatFloatPair =
                        googleService.calculateDurationDistance(origin, destination, new ArrayList<>(placeList));

                    tripTime += floatFloatPair.getFirst();
                    tripLength += floatFloatPair.getSecond();

                    TripDay tripDay = new TripDay();
                    tripDay = tripDay
                        .tripId(trip.getId())
                        .date(startDate.plusDays(dayIndex++))
                        .originId(origin == null ? null : origin.getId())
                        .destinationId(destination == null ? null : destination.getId())
                        .hours(floatFloatPair.getFirst())
                        .length(floatFloatPair.getSecond())
                        .photoURL(placeList.size() <= 2 ? origin.getPhotoURL() : placeList.get(2).getPhotoURL());
                    tripDay = tripDayRepository.save(tripDay);
                    for (Place placeForSaving : placeList) {
                        dayPlaceRepository.save(
                            new DayPlace()
                                .placeId(placeForSaving.getId())
                                .tripDayId(tripDay.getId())
                        );
                    }
                    origin = null;
                }
            }
            trip = trip
                .hours(tripTime)
                .length(tripLength);
            log.debug("REST request to save Trip : {}", trip);
            return tripRepository.save(trip);
        } catch (Exception e) {
            removeTrip(trip);
            throw new RuntimeException(e);
        }
    }


    public void updateTripDayParameters(String tripDayId) {
        TripDay tripDay = tripDayRepository.findOne(tripDayId);
        List<DayPlace> places = dayPlaceRepository.findByTripDayId(tripDayId);
        List<Place> placeList = new ArrayList<>();
        for (DayPlace dayPlace : places) {
            placeList.add(findPlaceOrNull(dayPlace.getPlaceId()));
        }
//        Place originPlace = findPlaceOrNull(tripDay.getOriginId());
//        Place destinationPlace = findPlaceOrNull(tripDay.getDestinationId());
//        Pair<Float, Float> results = googleService.calculateDurationDistance(originPlace, destinationPlace, placeList);
        tripDay.setHours(placeList.size() * 0.3523423423523f);
        tripDay.setLength(placeList.size() * 0.2432253124221f);
        tripDayRepository.save(tripDay);
    }

    public void removeTrip(Trip trip) {
        if (trip != null) {
            List<TripDay> tripDays = tripDayRepository.deleteByTripId(trip.getId());
            tripDays.forEach(tripDay -> {
                dayPlaceRepository.removeByTripDayId(tripDay.getId());
            });
            tripRepository.delete(trip);
        }
    }

    private Place findPlaceOrNull(String id) {
        return id == null ? null : placeRepository.findOne(id);
    }
}
