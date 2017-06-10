package com.routeadvisor.app.service.dto;


import com.routeadvisor.app.domain.Place;

import java.io.Serializable;
import java.util.List;

public class SearchResultDTO implements Serializable {

    List<Place> places;

    List<Place> addresses;

    List<Place> hotels;

    public SearchResultDTO() {
    }

    public SearchResultDTO(List<Place> places, List<Place> addresses, List<Place> hotels) {
        this.places = places;
        this.addresses = addresses;
        this.hotels = hotels;
    }

    public List<Place> getPlaces() {
        return places;
    }

    public void setPlaces(List<Place> places) {
        this.places = places;
    }

    public List<Place> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Place> addresses) {
        this.addresses = addresses;
    }

    public List<Place> getHotels() {
        return hotels;
    }

    public void setHotels(List<Place> hotels) {
        this.hotels = hotels;
    }
}
