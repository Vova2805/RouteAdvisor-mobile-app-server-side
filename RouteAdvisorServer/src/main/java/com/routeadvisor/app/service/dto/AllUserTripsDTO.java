package com.routeadvisor.app.service.dto;

import com.routeadvisor.app.domain.Trip;

import java.io.Serializable;
import java.util.List;

public class AllUserTripsDTO implements Serializable {

    private List<Trip> scheduled;
    private List<Trip> wishlist;
    private List<Trip> past;
    private List<Trip> trash;

    public AllUserTripsDTO() {
    }

    public AllUserTripsDTO(List<Trip> scheduled, List<Trip> wishlist, List<Trip> past, List<Trip> trash) {
        this.scheduled = scheduled;
        this.wishlist = wishlist;
        this.past = past;
        this.trash = trash;
    }

    public List<Trip> getScheduled() {
        return scheduled;
    }

    public void setScheduled(List<Trip> scheduled) {
        this.scheduled = scheduled;
    }

    public List<Trip> getWishlist() {
        return wishlist;
    }

    public void setWishlist(List<Trip> wishlist) {
        this.wishlist = wishlist;
    }

    public List<Trip> getPast() {
        return past;
    }

    public void setPast(List<Trip> past) {
        this.past = past;
    }

    public List<Trip> getTrash() {
        return trash;
    }

    public void setTrash(List<Trip> trash) {
        this.trash = trash;
    }
}
