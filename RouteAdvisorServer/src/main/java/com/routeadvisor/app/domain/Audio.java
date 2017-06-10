package com.routeadvisor.app.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Audio.
 */

@Document(collection = "audio")
public class Audio implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("place_id")
    private String placeId;

    @NotNull
    @Field("title")
    private String title;

    @NotNull
    @Field("artist")
    private String artist;

    @NotNull
    @Field("src")
    private String src;

    @Field("art")
    private String art;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlaceId() {
        return placeId;
    }

    public Audio placeId(String placeId) {
        this.placeId = placeId;
        return this;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getTitle() {
        return title;
    }

    public Audio title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public Audio artist(String artist) {
        this.artist = artist;
        return this;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getSrc() {
        return src;
    }

    public Audio src(String src) {
        this.src = src;
        return this;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getArt() {
        return art;
    }

    public Audio art(String art) {
        this.art = art;
        return this;
    }

    public void setArt(String art) {
        this.art = art;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Audio audio = (Audio) o;
        if (audio.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), audio.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Audio{" +
            "id=" + getId() +
            ", placeId='" + getPlaceId() + "'" +
            ", title='" + getTitle() + "'" +
            ", artist='" + getArtist() + "'" +
            ", src='" + getSrc() + "'" +
            ", art='" + getArt() + "'" +
            "}";
    }
}
