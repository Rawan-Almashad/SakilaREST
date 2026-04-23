package org.iti.rest.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class FilmActorId implements Serializable {

    private static final long serialVersionUID = 1313131672564555260L;

    @Column(name = "actor_id", columnDefinition = "smallint unsigned", nullable = false)
    private Short actorId;

    @Column(name = "film_id", columnDefinition = "smallint unsigned", nullable = false)
    private Short filmId;

    // Default constructor required by JPA
    public FilmActorId() {}

    public FilmActorId(Short actorId, Short filmId) {
        this.actorId = actorId;
        this.filmId = filmId;
    }

    // Getters & Setters
    public Short getActorId() { return actorId; }
    public void setActorId(Short actorId) { this.actorId = actorId; }

    public Short getFilmId() { return filmId; }
    public void setFilmId(Short filmId) { this.filmId = filmId; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FilmActorId that = (FilmActorId) o;
        return Objects.equals(actorId, that.actorId) && Objects.equals(filmId, that.filmId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(actorId, filmId);
    }
}