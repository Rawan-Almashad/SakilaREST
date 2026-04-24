package org.iti.rest.dto;

public class CityResponse {
    private Short id;
    private String city;
    private Short countryId;
    private String countryName;
    private String lastUpdate;  // Changed from Instant to String

    public CityResponse() {}

    public CityResponse(Short id, String city, Short countryId, String countryName, String lastUpdate) {
        this.id = id;
        this.city = city;
        this.countryId = countryId;
        this.countryName = countryName;
        this.lastUpdate = lastUpdate;
    }

    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Short getCountryId() {
        return countryId;
    }

    public void setCountryId(Short countryId) {
        this.countryId = countryId;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}