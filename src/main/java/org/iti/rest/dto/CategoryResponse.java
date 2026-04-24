package org.iti.rest.dto;

public class CategoryResponse {
    private Short categoryId;
    private String name;
    private String lastUpdate;  // String instead of Instant

    public CategoryResponse() {}

    public CategoryResponse(Short categoryId, String name, String lastUpdate) {
        this.categoryId = categoryId;
        this.name = name;
        this.lastUpdate = lastUpdate;
    }

    public Short getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Short categoryId) {
        this.categoryId = categoryId;
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