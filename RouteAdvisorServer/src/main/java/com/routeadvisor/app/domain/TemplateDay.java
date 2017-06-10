package com.routeadvisor.app.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A TemplateDay.
 */

@Document(collection = "template_day")
public class TemplateDay implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("template_id")
    private String templateId;

    @NotNull
    @Field("date")
    private ZonedDateTime date;

    @NotNull
    @Field("hours")
    private Float hours;

    @NotNull
    @Field("length")
    private Float length;

    @NotNull
    @Field("places_count")
    private Integer placesCount;

    @NotNull
    @Field("origin_id")
    private String originId;

    @NotNull
    @Field("destination_id")
    private String destinationId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTemplateId() {
        return templateId;
    }

    public TemplateDay templateId(String templateId) {
        this.templateId = templateId;
        return this;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public TemplateDay date(ZonedDateTime date) {
        this.date = date;
        return this;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public Float getHours() {
        return hours;
    }

    public TemplateDay hours(Float hours) {
        this.hours = hours;
        return this;
    }

    public void setHours(Float hours) {
        this.hours = hours;
    }

    public Float getLength() {
        return length;
    }

    public TemplateDay length(Float length) {
        this.length = length;
        return this;
    }

    public void setLength(Float length) {
        this.length = length;
    }

    public Integer getPlacesCount() {
        return placesCount;
    }

    public TemplateDay placesCount(Integer placesCount) {
        this.placesCount = placesCount;
        return this;
    }

    public void setPlacesCount(Integer placesCount) {
        this.placesCount = placesCount;
    }

    public String getOriginId() {
        return originId;
    }

    public TemplateDay originId(String originId) {
        this.originId = originId;
        return this;
    }

    public void setOriginId(String originId) {
        this.originId = originId;
    }

    public String getDestinationId() {
        return destinationId;
    }

    public TemplateDay destinationId(String destinationId) {
        this.destinationId = destinationId;
        return this;
    }

    public void setDestinationId(String destinationId) {
        this.destinationId = destinationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TemplateDay templateDay = (TemplateDay) o;
        if (templateDay.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), templateDay.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TemplateDay{" +
            "id=" + getId() +
            ", templateId='" + getTemplateId() + "'" +
            ", date='" + getDate() + "'" +
            ", hours='" + getHours() + "'" +
            ", length='" + getLength() + "'" +
            ", placesCount='" + getPlacesCount() + "'" +
            ", originId='" + getOriginId() + "'" +
            ", destinationId='" + getDestinationId() + "'" +
            "}";
    }
}
