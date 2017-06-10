package com.routeadvisor.app.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A Trip.
 */

@Document(collection = "trip")
public class Trip implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("user_id")
    private String userId;

    @NotNull
    @Field("city_id")
    private String cityId;

    @NotNull
    @Field("name")
    private String name;

    @Field("description")
    private String description;

    @NotNull
    @Field("photo_url")
    private String photoURL;

    @Field("start_date")
    private ZonedDateTime startDate;

    @Field("end_date")
    private ZonedDateTime endDate;

    @NotNull
    @Field("hours")
    private Float hours;

    @Field("cost")
    private Float cost;

    @NotNull
    @Field("length")
    private Float length;

    @Min(value = 0)
    @Field("notes_count")
    private Integer notesCount;

    @Field("arrival_id")
    private String arrivalId;

    @Field("accommodation_id")
    private String accommodationId;

    @Field("audios_ids")
    private String audiosIds;

    @Field("videos_ids")
    private String videosIds;

    @Field("dates_known")
    private Boolean datesKnown;

    @NotNull
    @Field("accessible")
    private Boolean accessible;

    @NotNull
    @Field("offline")
    private Boolean offline;

    @Field("trash")
    private Boolean trash;

    @Field("token")
    private String token;

    @Field("addedTemplateIds")
    private List<String> addedTemplateIds;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public Trip userId(String userId) {
        this.userId = userId;
        return this;
    }

    public Boolean getDatesKnown() {
        return datesKnown;
    }

    public Boolean getAccessible() {
        return accessible;
    }

    public Boolean getOffline() {
        return offline;
    }

    public Boolean getTrash() {
        return trash;
    }

    public List<String> getAddedTemplateIds() {
        if (Objects.isNull(addedTemplateIds)) {
            addedTemplateIds = new ArrayList<>();
        }
        return addedTemplateIds;
    }

    public void setAddedTemplateIds(List<String> addedTemplateIds) {
        this.addedTemplateIds = addedTemplateIds;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCityId() {
        return cityId;
    }

    public Trip cityId(String cityId) {
        this.cityId = cityId;
        return this;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getName() {
        return name;
    }

    public Trip name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Trip description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public Trip photoURL(String photoURL) {
        this.photoURL = photoURL;
        return this;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public ZonedDateTime getStartDate() {
        return startDate;
    }

    public Trip startDate(ZonedDateTime startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(ZonedDateTime startDate) {
        this.startDate = startDate;
    }

    public ZonedDateTime getEndDate() {
        return endDate;
    }

    public Trip endDate(ZonedDateTime endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(ZonedDateTime endDate) {
        this.endDate = endDate;
    }

    public Float getHours() {
        return hours;
    }

    public Trip hours(Float hours) {
        this.hours = hours;
        return this;
    }

    public void setHours(Float hours) {
        this.hours = hours;
    }

    public Float getCost() {
        return cost;
    }

    public Trip cost(Float cost) {
        this.cost = cost;
        return this;
    }

    public void setCost(Float cost) {
        this.cost = cost;
    }

    public Float getLength() {
        return length;
    }

    public Trip length(Float length) {
        this.length = length;
        return this;
    }

    public void setLength(Float length) {
        this.length = length;
    }

    public Integer getNotesCount() {
        return notesCount;
    }

    public Trip notesCount(Integer notesCount) {
        this.notesCount = notesCount;
        return this;
    }

    public void setNotesCount(Integer notesCount) {
        this.notesCount = notesCount;
    }

    public String getArrivalId() {
        return arrivalId;
    }

    public Trip arrivalId(String arrivalId) {
        this.arrivalId = arrivalId;
        return this;
    }

    public void setArrivalId(String arrivalId) {
        this.arrivalId = arrivalId;
    }

    public String getAccommodationId() {
        return accommodationId;
    }

    public Trip accommodationId(String accommodationId) {
        this.accommodationId = accommodationId;
        return this;
    }

    public void setAccommodationId(String accommodationId) {
        this.accommodationId = accommodationId;
    }

    public String getAudiosIds() {
        return audiosIds;
    }

    public Trip audiosIds(String audiosIds) {
        this.audiosIds = audiosIds;
        return this;
    }

    public void setAudiosIds(String audiosIds) {
        this.audiosIds = audiosIds;
    }

    public String getVideosIds() {
        return videosIds;
    }

    public Trip videosIds(String videosIds) {
        this.videosIds = videosIds;
        return this;
    }

    public void setVideosIds(String videosIds) {
        this.videosIds = videosIds;
    }

    public Boolean isDatesKnown() {
        return datesKnown;
    }

    public Trip datesKnown(Boolean datesKnown) {
        this.datesKnown = datesKnown;
        return this;
    }

    public void setDatesKnown(Boolean datesKnown) {
        this.datesKnown = datesKnown;
    }

    public Boolean isAccessible() {
        return accessible;
    }

    public Trip accessible(Boolean accessible) {
        this.accessible = accessible;
        return this;
    }

    public void setAccessible(Boolean accessible) {
        this.accessible = accessible;
    }

    public Boolean isOffline() {
        return offline;
    }

    public Trip offline(Boolean offline) {
        this.offline = offline;
        return this;
    }

    public void setOffline(Boolean offline) {
        this.offline = offline;
    }

    public Boolean isTrash() {
        return trash;
    }

    public Trip trash(Boolean trash) {
        this.trash = trash;
        return this;
    }

    public void setTrash(Boolean trash) {
        this.trash = trash;
    }

    public String getToken() {
        return token;
    }

    public Trip token(String token) {
        this.token = token;
        return this;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Trip trip = (Trip) o;
        if (trip.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), trip.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Trip{" +
            "id=" + getId() +
            ", userId='" + getUserId() + "'" +
            ", cityId='" + getCityId() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", photoURL='" + getPhotoURL() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", hours='" + getHours() + "'" +
            ", cost='" + getCost() + "'" +
            ", length='" + getLength() + "'" +
            ", notesCount='" + getNotesCount() + "'" +
            ", arrivalId='" + getArrivalId() + "'" +
            ", accommodationId='" + getAccommodationId() + "'" +
            ", audiosIds='" + getAudiosIds() + "'" +
            ", videosIds='" + getVideosIds() + "'" +
            ", datesKnown='" + isDatesKnown() + "'" +
            ", accessible='" + isAccessible() + "'" +
            ", offline='" + isOffline() + "'" +
            ", trash='" + isTrash() + "'" +
            ", token='" + getToken() + "'" +
            "}";
    }
}
