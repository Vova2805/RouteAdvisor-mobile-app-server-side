package com.routeadvisor.app.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import com.routeadvisor.app.domain.enumeration.FeatureLevel;

import com.routeadvisor.app.domain.enumeration.FeatureType;

import com.routeadvisor.app.domain.enumeration.Category;

/**
 * A Feature.
 */

@Document(collection = "feature")
public class Feature implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("level")
    private FeatureLevel level;

    @NotNull
    @Field("type")
    private FeatureType type;

    @NotNull
    @DecimalMin(value = "0")
    @DecimalMax(value = "5")
    @Field("rating")
    private Double rating;

    @Field("guid")
    private String guid;

    @NotNull
    @Field("name")
    private String name;

    @Field("name_suffix")
    private String nameSuffix;

    @Field("original_name")
    private String originalName;

    @NotNull
    @Field("description")
    private String description;

    @Field("url")
    private String url;

    @Field("address")
    private String address;

    @Field("price")
    private Double price;

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

    @Field("tier")
    private Integer tier;

    @NotNull
    @Field("proposed_duration")
    private Integer proposedDuration;

    @NotNull
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public FeatureLevel getLevel() {
        return level;
    }

    public Feature level(FeatureLevel level) {
        this.level = level;
        return this;
    }

    public void setLevel(FeatureLevel level) {
        this.level = level;
    }

    public FeatureType getType() {
        return type;
    }

    public Feature type(FeatureType type) {
        this.type = type;
        return this;
    }

    public void setType(FeatureType type) {
        this.type = type;
    }

    public Double getRating() {
        return rating;
    }

    public Feature rating(Double rating) {
        this.rating = rating;
        return this;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getGuid() {
        return guid;
    }

    public Feature guid(String guid) {
        this.guid = guid;
        return this;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getName() {
        return name;
    }

    public Feature name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameSuffix() {
        return nameSuffix;
    }

    public Feature nameSuffix(String nameSuffix) {
        this.nameSuffix = nameSuffix;
        return this;
    }

    public void setNameSuffix(String nameSuffix) {
        this.nameSuffix = nameSuffix;
    }

    public String getOriginalName() {
        return originalName;
    }

    public Feature originalName(String originalName) {
        this.originalName = originalName;
        return this;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getDescription() {
        return description;
    }

    public Feature description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public Feature url(String url) {
        this.url = url;
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAddress() {
        return address;
    }

    public Feature address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getPrice() {
        return price;
    }

    public Feature price(Double price) {
        this.price = price;
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public Feature currency(String currency) {
        this.currency = currency;
        return this;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public Feature photoURL(String photoURL) {
        this.photoURL = photoURL;
        return this;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public Double getLat() {
        return lat;
    }

    public Feature lat(Double lat) {
        this.lat = lat;
        return this;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public Feature lng(Double lng) {
        this.lng = lng;
        return this;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Category getCategory() {
        return category;
    }

    public Feature category(Category category) {
        this.category = category;
        return this;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getTags() {
        return tags;
    }

    public Feature tags(String tags) {
        this.tags = tags;
        return this;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public ZonedDateTime getEditedAt() {
        return editedAt;
    }

    public Feature editedAt(ZonedDateTime editedAt) {
        this.editedAt = editedAt;
        return this;
    }

    public void setEditedAt(ZonedDateTime editedAt) {
        this.editedAt = editedAt;
    }

    public Integer getTier() {
        return tier;
    }

    public Feature tier(Integer tier) {
        this.tier = tier;
        return this;
    }

    public void setTier(Integer tier) {
        this.tier = tier;
    }

    public Integer getProposedDuration() {
        return proposedDuration;
    }

    public Feature proposedDuration(Integer proposedDuration) {
        this.proposedDuration = proposedDuration;
        return this;
    }

    public void setProposedDuration(Integer proposedDuration) {
        this.proposedDuration = proposedDuration;
    }

    public Integer getFavorites() {
        return favorites;
    }

    public Feature favorites(Integer favorites) {
        this.favorites = favorites;
        return this;
    }

    public void setFavorites(Integer favorites) {
        this.favorites = favorites;
    }

    public String getOfficialWebsite() {
        return officialWebsite;
    }

    public Feature officialWebsite(String officialWebsite) {
        this.officialWebsite = officialWebsite;
        return this;
    }

    public void setOfficialWebsite(String officialWebsite) {
        this.officialWebsite = officialWebsite;
    }

    public String getOpeningHours() {
        return openingHours;
    }

    public Feature openingHours(String openingHours) {
        this.openingHours = openingHours;
        return this;
    }

    public void setOpeningHours(String openingHours) {
        this.openingHours = openingHours;
    }

    public String getEmail() {
        return email;
    }

    public Feature email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Feature phoneNumber(String phoneNumber) {
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
        Feature feature = (Feature) o;
        if (feature.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), feature.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Feature{" +
            "id=" + getId() +
            ", level='" + getLevel() + "'" +
            ", type='" + getType() + "'" +
            ", rating='" + getRating() + "'" +
            ", guid='" + getGuid() + "'" +
            ", name='" + getName() + "'" +
            ", nameSuffix='" + getNameSuffix() + "'" +
            ", originalName='" + getOriginalName() + "'" +
            ", description='" + getDescription() + "'" +
            ", url='" + getUrl() + "'" +
            ", address='" + getAddress() + "'" +
            ", price='" + getPrice() + "'" +
            ", currency='" + getCurrency() + "'" +
            ", photoURL='" + getPhotoURL() + "'" +
            ", lat='" + getLat() + "'" +
            ", lng='" + getLng() + "'" +
            ", category='" + getCategory() + "'" +
            ", tags='" + getTags() + "'" +
            ", editedAt='" + getEditedAt() + "'" +
            ", tier='" + getTier() + "'" +
            ", proposedDuration='" + getProposedDuration() + "'" +
            ", favorites='" + getFavorites() + "'" +
            ", officialWebsite='" + getOfficialWebsite() + "'" +
            ", openingHours='" + getOpeningHours() + "'" +
            ", email='" + getEmail() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            "}";
    }
}
