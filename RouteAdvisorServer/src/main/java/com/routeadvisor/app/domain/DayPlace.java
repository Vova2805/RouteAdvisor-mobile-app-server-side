package com.routeadvisor.app.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DayPlace.
 */

@Document(collection = "day_place")
public class DayPlace implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("place_id")
    private String placeId;

    @Field("trip_day_id")
    private String tripDayId;

    @Field("template_day_id")
    private String templateDayId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlaceId() {
        return placeId;
    }

    public DayPlace placeId(String placeId) {
        this.placeId = placeId;
        return this;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getTripDayId() {
        return tripDayId;
    }

    public DayPlace tripDayId(String tripDayId) {
        this.tripDayId = tripDayId;
        return this;
    }

    public void setTripDayId(String tripDayId) {
        this.tripDayId = tripDayId;
    }

    public String getTemplateDayId() {
        return templateDayId;
    }

    public DayPlace templateDayId(String templateDayId) {
        this.templateDayId = templateDayId;
        return this;
    }

    public void setTemplateDayId(String templateDayId) {
        this.templateDayId = templateDayId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DayPlace dayPlace = (DayPlace) o;
        if (dayPlace.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dayPlace.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DayPlace{" +
            "id=" + getId() +
            ", placeId='" + getPlaceId() + "'" +
            ", tripDayId='" + getTripDayId() + "'" +
            ", templateDayId='" + getTemplateDayId() + "'" +
            "}";
    }
}
