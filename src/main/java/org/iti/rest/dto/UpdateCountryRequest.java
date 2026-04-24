package org.iti.rest.dto;

import jakarta.xml.bind.annotation.XmlRootElement;

public class UpdateCountryRequest {

    private Short id;
    private String country;

    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}