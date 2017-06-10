package com.routeadvisor.app.service;

import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DirectionsLeg;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.LatLng;
import com.routeadvisor.app.domain.Place;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GoogleService {

    private static final String KEY = "AIzaSyBO4HU5LUzTh99KrFxsOhH_b9iQYD-3lN0";

    public Pair<Float, Float> calculateDurationDistance(Place originPlace,
                                                        Place destinationPlace,
                                                        List<Place> places) {
        places.add(0, originPlace);
        places.add(destinationPlace);

        GeoApiContext context = new GeoApiContext().setApiKey(KEY);
        if (destinationPlace == null && places.size() > 2) {
            destinationPlace = places.get(places.size() - 1);
        }
        if (originPlace == null || destinationPlace == null) {
            return Pair.of(0f, 0f);
        }
        LatLng origin = new LatLng(originPlace.getLat(), originPlace.getLng());
        LatLng destination = new LatLng(destinationPlace.getLat(), destinationPlace.getLng());
        List<LatLng> waypoints = new ArrayList<>();
        places.forEach(place -> {
            if (place != null && place.getLat() != null && place.getLng() != null) {
                waypoints.add(new LatLng(place.getLat(), place.getLng()));
            }
        });
        LatLng[] array = new LatLng[waypoints.size()];
        waypoints.toArray(array);
        DirectionsApiRequest directions = DirectionsApi
            .newRequest(context)
            .origin(origin)
            .destination(destination)
            .waypoints(array)
            .optimizeWaypoints(true);
        DirectionsResult directionsResult = directions.awaitIgnoreError();
        if (directionsResult.routes.length > 0) {
            DirectionsLeg leg = directionsResult.routes[0].legs[0];
            long inMeters = leg.distance.inMeters;
            long inSeconds = leg.duration.inSeconds;
            for (Place place : places) {
                inSeconds += place.getProposedDuration() * 60;
            }
            return Pair.of(secondsToHours(inSeconds), meterToKM(inMeters));
        } else {
            return Pair.of(0f, 0f);
        }
    }

    private Float meterToKM(long meters) {
        return meters / 1000.0f;
    }

    private Float secondsToHours(long seconds) {
        return seconds / 3600.0f;
    }
}
