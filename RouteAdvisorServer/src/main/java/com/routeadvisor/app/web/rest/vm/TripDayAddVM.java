package com.routeadvisor.app.web.rest.vm;

public class TripDayAddVM {

    private String templateId;
    private String placeId;

    public TripDayAddVM() {
    }

    public TripDayAddVM(String templateId, String placeId) {
        this.templateId = templateId;
        this.placeId = placeId;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }
}
