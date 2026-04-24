package org.iti.rest.dto;

public class LanguageResponse {
    private Short id;
    private String name;
    private String lastUpdate;  // String instead of Instant

    public LanguageResponse() {}

    public LanguageResponse(Short id, String name, String lastUpdate) {
        this.id = id;
        this.name = name;
        this.lastUpdate = lastUpdate;
    }

    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}