package com.routeadvisor.app.web.rest.vm;

import javax.validation.constraints.NotNull;

public class TripVM {
    @NotNull
    private String name;
    private String description;
    @NotNull
    private City city;
    private Pattern template;
    private Boolean datesKnown;
    private String startDate;
    private String endDate;
    private Place arrival;
    private Place accommodation;

    public TripVM() {
    }

    public class City {
        @NotNull
        private String id;

        public City() {
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

    public class Pattern {
        @NotNull
        private String id;

        public Pattern() {
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

    public class Place {
        @NotNull
        private String id;

        public Place() {
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Pattern getTemplate() {
        return template;
    }

    public void setTemplate(Pattern template) {
        this.template = template;
    }

    public Boolean getDatesKnown() {
        return datesKnown;
    }

    public void setDatesKnown(Boolean datesKnown) {
        this.datesKnown = datesKnown;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Place getArrival() {
        return arrival;
    }

    public void setArrival(Place arrival) {
        this.arrival = arrival;
    }

    public Place getAccommodation() {
        return accommodation;
    }

    public void setAccommodation(Place accommodation) {
        this.accommodation = accommodation;
    }
}
