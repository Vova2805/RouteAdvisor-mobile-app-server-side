package com.routeadvisor.app.domain;

import com.routeadvisor.app.domain.enumeration.TemplateType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Template.
 */

@Document(collection = "template")
public class Template implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("city_id")
    private String cityId;

    @Field("city")
    private String city;

    @NotNull
    @Field("name")
    private String name;

    @NotNull
    @Field("description")
    private String description;

    @NotNull
    @Min(value = 0)
    @Field("time")
    private Integer time;

    @NotNull
    @Min(value = 0)
    @Field("days")
    private Integer days;

    @NotNull
    @DecimalMin(value = "0")
    @DecimalMax(value = "5")
    @Field("rating")
    private Float rating;

    @NotNull
    @Field("photo_url")
    private String photoURL;

    @NotNull
    @Field("length")
    private Float length;

    @NotNull
    @Field("places_count")
    private Integer placesCount;

    @NotNull
    @Field("totalRating")
    private Float totalRating;

    @NotNull
    @Field("reviews")
    private Integer reviews;

    @NotNull
    @Field("type")
    private TemplateType type;

    @DecimalMin(value = "0")
    @Field("price")
    private Float price;

    @Field("currency")
    private String currency;

    @Field("website")
    private String website;

    @Field("phone_number")
    private String phoneNumber;

    @Field("inTrip")
    private Boolean inTrip;

    @Field("tripId")
    private String tripId;

    @Field("tripName")
    private String tripName;

    public Boolean getInTrip() {
        return inTrip;
    }

    public void setInTrip(Boolean inTrip) {
        this.inTrip = inTrip;
    }

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public String getTripName() {
        return tripName;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCityId() {
        return cityId;
    }

    public Template cityId(String cityId) {
        this.cityId = cityId;
        return this;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getName() {
        return name;
    }

    public Template name(String name) {
        this.name = name;
        return this;
    }

    public Float getTotalRating() {
        return totalRating;
    }

    public void setTotalRating(Float totalRating) {
        this.totalRating = totalRating;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Template description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getTime() {
        return time;
    }

    public Template time(Integer time) {
        this.time = time;
        return this;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public Integer getDays() {
        return days;
    }

    public Template days(Integer days) {
        this.days = days;
        return this;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public Float getRating() {
        return rating;
    }

    public Template rating(Float rating) {
        this.rating = rating;
        return this;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public Template photoURL(String photoURL) {
        this.photoURL = photoURL;
        return this;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public Float getLength() {
        return length;
    }

    public Template length(Float length) {
        this.length = length;
        return this;
    }

    public void setLength(Float length) {
        this.length = length;
    }

    public Integer getPlacesCount() {
        return placesCount;
    }

    public Template placesCount(Integer placesCount) {
        this.placesCount = placesCount;
        return this;
    }

    public void setPlacesCount(Integer placesCount) {
        this.placesCount = placesCount;
    }

    public Integer getReviews() {
        return reviews;
    }

    public Template reviews(Integer reviews) {
        this.reviews = reviews;
        return this;
    }

    public void setReviews(Integer reviews) {
        this.reviews = reviews;
    }

    public TemplateType getType() {
        return type;
    }

    public Template type(TemplateType type) {
        this.type = type;
        return this;
    }

    public void setType(TemplateType type) {
        this.type = type;
    }

    public Float getPrice() {
        return price;
    }

    public Template price(Float price) {
        this.price = price;
        return this;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public Template currency(String currency) {
        this.currency = currency;
        return this;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getWebsite() {
        return website;
    }

    public Template website(String website) {
        this.website = website;
        return this;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Template phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Template template = (Template) o;
        if (template.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), template.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Template{" +
            "id=" + getId() +
            ", cityId='" + getCityId() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", time='" + getTime() + "'" +
            ", days='" + getDays() + "'" +
            ", rating='" + getRating() + "'" +
            ", photoURL='" + getPhotoURL() + "'" +
            ", length='" + getLength() + "'" +
            ", placesCount='" + getPlacesCount() + "'" +
            ", reviews='" + getReviews() + "'" +
            ", type='" + getType() + "'" +
            ", price='" + getPrice() + "'" +
            ", currency='" + getCurrency() + "'" +
            ", website='" + getWebsite() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            "}";
    }
}
