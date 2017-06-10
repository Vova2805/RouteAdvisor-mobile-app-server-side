package com.routeadvisor.app.service.dto;


import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the Trip entity.
 */
public class TripDTO implements Serializable {

    private String id;

    @NotNull
    private String name;

    private ZonedDateTime startDate;

    private ZonedDateTime endDate;

    @NotNull
    private String photoURL;

    @NotNull
    @DecimalMin(value = "0")
    private Float time;

    @NotNull
    @DecimalMin(value = "0")
    private Float cost;

    private String arrival;

    private String accommodation;

    @NotNull
    private Boolean datesKnown;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ZonedDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(ZonedDateTime startDate) {
        this.startDate = startDate;
    }

    public ZonedDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(ZonedDateTime endDate) {
        this.endDate = endDate;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public Float getTime() {
        return time;
    }

    public void setTime(Float time) {
        this.time = time;
    }

    public Float getCost() {
        return cost;
    }

    public void setCost(Float cost) {
        this.cost = cost;
    }

    public String getArrival() {
        return arrival;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
    }

    public String getAccommodation() {
        return accommodation;
    }

    public void setAccommodation(String accommodation) {
        this.accommodation = accommodation;
    }

    public Boolean isDatesKnown() {
        return datesKnown;
    }

    public void setDatesKnown(Boolean datesKnown) {
        this.datesKnown = datesKnown;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TripDTO tripDTO = (TripDTO) o;
        if (tripDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tripDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TripDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", photoURL='" + getPhotoURL() + "'" +
            ", time='" + getTime() + "'" +
            ", cost='" + getCost() + "'" +
            ", arrival='" + getArrival() + "'" +
            ", accommodation='" + getAccommodation() + "'" +
            ", datesKnown='" + isDatesKnown() + "'" +
            "}";
    }
}
