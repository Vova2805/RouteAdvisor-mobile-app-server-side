package com.routeadvisor.app.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

import com.routeadvisor.app.domain.enumeration.Category;

/**
 * A TopRecommended.
 */

@Document(collection = "top_recommended")
public class TopRecommended implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("rating")
    private Float rating;

    @NotNull
    @Field("category")
    private Category category;

    @Field("place_id")
    private String placeId;

    @Field("template_id")
    private String templateId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Float getRating() {
        return rating;
    }

    public TopRecommended rating(Float rating) {
        this.rating = rating;
        return this;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public Category getCategory() {
        return category;
    }

    public TopRecommended category(Category category) {
        this.category = category;
        return this;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getPlaceId() {
        return placeId;
    }

    public TopRecommended placeId(String placeId) {
        this.placeId = placeId;
        return this;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getTemplateId() {
        return templateId;
    }

    public TopRecommended templateId(String templateId) {
        this.templateId = templateId;
        return this;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TopRecommended topRecommended = (TopRecommended) o;
        if (topRecommended.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), topRecommended.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TopRecommended{" +
            "id=" + getId() +
            ", rating='" + getRating() + "'" +
            ", category='" + getCategory() + "'" +
            ", placeId='" + getPlaceId() + "'" +
            ", templateId='" + getTemplateId() + "'" +
            "}";
    }
}
