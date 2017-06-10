package com.routeadvisor.app.domain;

import com.routeadvisor.app.domain.enumeration.Category;
import com.routeadvisor.app.domain.enumeration.PlaceLevel;
import com.routeadvisor.app.domain.enumeration.PlaceType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Place.
 */

@Document(collection = "place")
public class Place implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    //    @NotNull
    @Field("city_id")
    private String cityId;

    @NotNull
    @Field("level")
    private PlaceLevel level;

    @NotNull
    @Field("type")
    private PlaceType type;

    @NotNull
    @DecimalMin(value = "0")
    @DecimalMax(value = "5")
    @Field("rating")
    private Float rating;

    @NotNull
    @Field("name")
    private String name;

    @NotNull
    @Field("description")
    private String description;

    @Field("address")
    private String address;

    @Field("price")
    private Float price;

    @Field("currency")
    private String currency;

    @NotNull
    @Field("photo_url")
    private String photoURL;

    @NotNull
    @Field("lat")
    private Double lat;

    @NotNull
    @Field("lng")
    private Double lng;

    @NotNull
    @Field("category")
    private Category category;

    @Field("tags")
    private String tags;

    @Field("edited_at")
    private ZonedDateTime editedAt;

    @NotNull
    @Field("proposed_duration")
    private Integer proposedDuration;

//    @NotNull
//    @NotNull
    @Min(value = 0)
    @Field("favorites")
    private Integer favorites;

    @Field("official_website")
    private String officialWebsite;

    @Field("opening_hours")
    private String openingHours;

    @Field("email")
    private String email;

    @Field("phone_number")
    private String phoneNumber;

//    @NotNull
    @Field("totalRating")
    private Float totalRating;

//    @NotNull
    @Field("reviews")
    private Integer reviews;

    @Field("inTrip")
    private Boolean inTrip;

    @Field("tripName")
    private String tripName;

    @Field("tripDayDate")
    private ZonedDateTime tripDayDate;

    @Field("tripDayId")
    private String tripDayId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCityId() {
        return cityId;
    }

    public Place cityId(String cityId) {
        this.cityId = cityId;
        return this;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public PlaceLevel getLevel() {
        return level;
    }

    public Place level(PlaceLevel level) {
        this.level = level;
        return this;
    }

    public void setLevel(PlaceLevel level) {
        this.level = level;
    }

    public PlaceType getType() {
        return type;
    }

    public Place type(PlaceType type) {
        this.type = type;
        return this;
    }

    public void setType(PlaceType type) {
        this.type = type;
    }

    public Float getRating() {
        return rating;
    }

    public Place rating(Float rating) {
        this.rating = rating;
        return this;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public Place name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Place description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public Place address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Float getPrice() {
        return price;
    }

    public Place price(Float price) {
        this.price = price;
        return this;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public Place currency(String currency) {
        this.currency = currency;
        return this;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public Place photoURL(String photoURL) {
        this.photoURL = photoURL;
        return this;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public Double getLat() {
        return lat;
    }

    public Place lat(Double lat) {
        this.lat = lat;
        return this;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public Place lng(Double lng) {
        this.lng = lng;
        return this;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Category getCategory() {
        return category;
    }

    public Place category(Category category) {
        this.category = category;
        return this;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getTags() {
        return tags;
    }

    public Place tags(String tags) {
        this.tags = tags;
        return this;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public ZonedDateTime getEditedAt() {
        return editedAt;
    }

    public Place editedAt(ZonedDateTime editedAt) {
        this.editedAt = editedAt;
        return this;
    }

    public void setEditedAt(ZonedDateTime editedAt) {
        this.editedAt = editedAt;
    }

    public Integer getProposedDuration() {
        return proposedDuration;
    }

    public Place proposedDuration(Integer proposedDuration) {
        this.proposedDuration = proposedDuration;
        return this;
    }

    public ZonedDateTime getTripDayDate() {
        return tripDayDate;
    }

    public void setTripDayDate(ZonedDateTime tripDayDate) {
        this.tripDayDate = tripDayDate;
    }

    public void setProposedDuration(Integer proposedDuration) {
        this.proposedDuration = proposedDuration;
    }

    public Integer getFavorites() {
        return favorites;
    }

    public Place favorites(Integer favorites) {
        this.favorites = favorites;
        return this;
    }

    public void setFavorites(Integer favorites) {
        this.favorites = favorites;
    }

    public String getOfficialWebsite() {
        return officialWebsite;
    }

    public Place officialWebsite(String officialWebsite) {
        this.officialWebsite = officialWebsite;
        return this;
    }

    public void setOfficialWebsite(String officialWebsite) {
        this.officialWebsite = officialWebsite;
    }

    public String getOpeningHours() {
        return openingHours;
    }

    public Place openingHours(String openingHours) {
        this.openingHours = openingHours;
        return this;
    }

    public void setOpeningHours(String openingHours) {
        this.openingHours = openingHours;
    }

    public String getEmail() {
        return email;
    }

    public Place email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Place phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getTripName() {
        return tripName;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Float getTotalRating() {
        return totalRating;
    }

    public void setTotalRating(Float totalRating) {
        this.totalRating = totalRating;
    }

    public Integer getReviews() {
        return reviews;
    }

    public void setReviews(Integer reviews) {
        this.reviews = reviews;
    }

    public Boolean getInTrip() {
        return inTrip;
    }

    public void setInTrip(Boolean inTrip) {
        this.inTrip = inTrip;
    }

    public String getTripDayId() {
        return tripDayId;
    }

    public void setTripDayId(String tripDayId) {
        this.tripDayId = tripDayId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Place place = (Place) o;
        if (place.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), place.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Place{" +
            "id=" + getId() +
            ", cityId='" + getCityId() + "'" +
            ", level='" + getLevel() + "'" +
            ", type='" + getType() + "'" +
            ", rating='" + getRating() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", address='" + getAddress() + "'" +
            ", price='" + getPrice() + "'" +
            ", currency='" + getCurrency() + "'" +
            ", photoURL='" + getPhotoURL() + "'" +
            ", lat='" + getLat() + "'" +
            ", lng='" + getLng() + "'" +
            ", category='" + getCategory() + "'" +
            ", tags='" + getTags() + "'" +
            ", editedAt='" + getEditedAt() + "'" +
            ", proposedDuration='" + getProposedDuration() + "'" +
            ", favorites='" + getFavorites() + "'" +
            ", officialWebsite='" + getOfficialWebsite() + "'" +
            ", openingHours='" + getOpeningHours() + "'" +
            ", email='" + getEmail() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            "}";
    }
}
