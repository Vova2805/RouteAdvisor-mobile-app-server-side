package com.routeadvisor.app.service.dto;


import java.io.Serializable;

public class NameNumberDTO implements Serializable {

    private String name;
    private int value;
    private boolean selected;

    public NameNumberDTO() {
    }

    public NameNumberDTO(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public NameNumberDTO(String name, int value, boolean selected) {
        this.name = name;
        this.value = value;
        this.selected = selected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
