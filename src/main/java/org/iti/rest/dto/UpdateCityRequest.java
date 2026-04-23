package org.iti.rest.dto;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UpdateCityRequest {
    private Short id;
    private String city;
    private Short countryId;

    public Short getId() { return id; }
    public void setId(Short id) { this.id = id; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public Short getCountryId() { return countryId; }
    public void setCountryId(Short countryId) { this.countryId = countryId; }
}