package org.iti.rest.dto;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CreateCityRequest {

    private String city;
    private Short countryId;

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
}