package org.iti.rest.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class FilmCategoryId implements Serializable {

    private static final long serialVersionUID = 1L;

    // film_id is SMALLINT in Sakila
    @Column(name = "film_id", columnDefinition = "smallint unsigned", nullable = false)
    private Short filmId;

    // 🔥 category_id is TINYINT in Sakila - THIS IS THE FIX
    @Column(name = "category_id", columnDefinition = "tinyint unsigned", nullable = false)
    private Short categoryId;

    public FilmCategoryId() {}

    public FilmCategoryId(Short filmId, Short categoryId) {
        this.filmId = filmId;
        this.categoryId = categoryId;
    }

    public Short getFilmId() { return filmId; }
    public void setFilmId(Short filmId) { this.filmId = filmId; }

    public Short getCategoryId() { return categoryId; }
    public void setCategoryId(Short categoryId) { this.categoryId = categoryId; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FilmCategoryId that = (FilmCategoryId) o;
        return Objects.equals(filmId, that.filmId) && Objects.equals(categoryId, that.categoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(filmId, categoryId);
    }
}