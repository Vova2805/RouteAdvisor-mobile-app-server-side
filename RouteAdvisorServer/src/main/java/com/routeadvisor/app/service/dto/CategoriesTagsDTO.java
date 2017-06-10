package com.routeadvisor.app.service.dto;


import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class CategoriesTagsDTO implements Serializable {

    private List<NameNumberDTO> categories;

    private List<NameNumberDTO> tags;

    public CategoriesTagsDTO() {
    }

    public CategoriesTagsDTO(List<NameNumberDTO> categories, List<NameNumberDTO> tags) {
        this.categories = categories;
        this.tags = tags;
    }

    public List<NameNumberDTO> getCategories() {
        return categories;
    }

    public void setCategories(List<NameNumberDTO> categories) {
        this.categories = categories;
    }

    public List<NameNumberDTO> getTags() {
        return tags;
    }

    public void setTags(List<NameNumberDTO> tags) {
        this.tags = tags;
    }
}
