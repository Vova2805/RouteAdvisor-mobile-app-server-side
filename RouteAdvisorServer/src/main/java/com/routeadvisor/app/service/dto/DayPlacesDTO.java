package com.routeadvisor.app.service.dto;

import java.util.ArrayList;
import java.util.List;

public class DayPlacesDTO<D, P> {

    private D day;
    private List<P> places;

    public DayPlacesDTO() {
        this.places = new ArrayList<P>();
    }

    public DayPlacesDTO(D day, List<P> places) {
        this.day = day;
        this.places = places;
    }

    public D getDay() {
        return day;
    }

    public void setDay(D day) {
        this.day = day;
    }

    public List<P> getPlaces() {
        return places;
    }

    public void setPlaces(List<P> places) {
        this.places = places;
    }
}
