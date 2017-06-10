package com.routeadvisor.app.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Photo.
 */

@Document(collection = "photo")
public class Photo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("place_id")
    private String placeId;

    @NotNull
    @Field("src")
    private String src;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlaceId() {
        return placeId;
    }

    public Photo placeId(String placeId) {
        this.placeId = placeId;
        return this;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getSrc() {
        return src;
    }

    public Photo src(String src) {
        this.src = src;
        return this;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Photo photo = (Photo) o;
        if (photo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), photo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Photo{" +
            "id=" + getId() +
            ", placeId='" + getPlaceId() + "'" +
            ", src='" + getSrc() + "'" +
            "}";
    }
}
