package com.routeadvisor.app.service.dto;

import java.io.Serializable;
import java.util.List;

public class RecommendedResultsDTO<T, D> implements Serializable {

    private List<T> recommended;
    private List<T> result;
    private D categoriesTags;
    private boolean inTrip;
    private boolean custom;
    private boolean favorited;

    public RecommendedResultsDTO() {
    }

    public RecommendedResultsDTO(List<T> recommended, List<T> result) {
        this.recommended = recommended;
        this.result = result;
    }

    public RecommendedResultsDTO(List<T> recommended, List<T> result, D categoriesTags) {
        this.recommended = recommended;
        this.result = result;
        this.categoriesTags = categoriesTags;
    }

    public RecommendedResultsDTO(List<T> recommended, List<T> result, D categoriesTags, boolean inTrip, boolean custom, boolean favorited) {
        this.recommended = recommended;
        this.result = result;
        this.categoriesTags = categoriesTags;
        this.inTrip = inTrip;
        this.custom = custom;
        this.favorited = favorited;
    }

    public boolean isCustom() {
        return custom;
    }

    public void setCustom(boolean custom) {
        this.custom = custom;
    }

    public boolean isFavorited() {
        return favorited;
    }

    public void setFavorited(boolean favorited) {
        this.favorited = favorited;
    }

    public List<T> getRecommended() {
        return recommended;
    }

    public void setRecommended(List<T> recommended) {
        this.recommended = recommended;
    }

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }

    public D getCategoriesTags() {
        return categoriesTags;
    }

    public void setCategoriesTags(D categoriesTags) {
        this.categoriesTags = categoriesTags;
    }

    public boolean isInTrip() {
        return inTrip;
    }

    public void setInTrip(boolean inTrip) {
        this.inTrip = inTrip;
    }
}
