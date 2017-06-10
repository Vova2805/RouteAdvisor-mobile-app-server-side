package com.routeadvisor.app.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A TripDay.
 */

@Document(collection = "trip_day")
public class TripDay implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("trip_id")
    private String tripId;

    @NotNull
    @Field("date")
    private ZonedDateTime date;

    @NotNull
    @Field("hours")
    private Float hours;

    @Field("length")
    private Float length;

    @Field("origin_id")
    private String originId;

    @Field("destination_id")
    private String destinationId;

    @Field("photo_url")
    private String photoURL;

    @Field("images")
    private String images;

    @Field("audios")
    private String audios;

    @Field("videos")
    private String videos;

    @Field("tripName")
    private String tripName;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTripId() {
        return tripId;
    }

    public TripDay tripId(String tripId) {
        this.tripId = tripId;
        return this;
    }

    public String getTripName() {
        return tripName;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public TripDay date(ZonedDateTime date) {
        this.date = date;
        return this;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public Float getHours() {
        return hours;
    }

    public TripDay hours(Float hours) {
        this.hours = hours;
        return this;
    }

    public void setHours(Float hours) {
        this.hours = hours;
    }

    public Float getLength() {
        return length;
    }

    public TripDay length(Float length) {
        this.length = length;
        return this;
    }

    public void setLength(Float length) {
        this.length = length;
    }

    public String getOriginId() {
        return originId;
    }

    public TripDay originId(String originId) {
        this.originId = originId;
        return this;
    }

    public void setOriginId(String originId) {
        this.originId = originId;
    }

    public String getDestinationId() {
        return destinationId;
    }

    public TripDay destinationId(String destinationId) {
        this.destinationId = destinationId;
        return this;
    }

    public void setDestinationId(String destinationId) {
        this.destinationId = destinationId;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public TripDay photoURL(String photoURL) {
        this.photoURL = photoURL;
        return this;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public String getImages() {
        return images;
    }

    public TripDay images(String images) {
        this.images = images;
        return this;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getAudios() {
        return audios;
    }

    public TripDay audios(String audios) {
        this.audios = audios;
        return this;
    }

    public void setAudios(String audios) {
        this.audios = audios;
    }

    public String getVideos() {
        return videos;
    }

    public TripDay videos(String videos) {
        this.videos = videos;
        return this;
    }

    public void setVideos(String videos) {
        this.videos = videos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TripDay tripDay = (TripDay) o;
        if (tripDay.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tripDay.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TripDay{" +
            "id=" + getId() +
            ", tripId='" + getTripId() + "'" +
            ", date='" + getDate() + "'" +
            ", hours='" + getHours() + "'" +
            ", length='" + getLength() + "'" +
            ", originId='" + getOriginId() + "'" +
            ", destinationId='" + getDestinationId() + "'" +
            ", photoURL='" + getPhotoURL() + "'" +
            ", images='" + getImages() + "'" +
            ", audios='" + getAudios() + "'" +
            ", videos='" + getVideos() + "'" +
            "}";
    }
}
