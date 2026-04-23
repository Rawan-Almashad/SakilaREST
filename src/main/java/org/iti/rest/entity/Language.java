package org.iti.rest.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import java.time.Instant;

@Entity
@Table(name = "language")
public class Language {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "language_id", columnDefinition = "tinyint unsigned", nullable = false)  // 🔥 THIS IS THE FIX
    private Short languageId;

    @Column(name = "name", nullable = false, columnDefinition = "char(20)")
    private String name;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "last_update", nullable = false)
    private Instant lastUpdate;

    // Getters & Setters
    public Short getLanguageId() { return languageId; }
    public void setLanguageId(Short languageId) { this.languageId = languageId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Instant getLastUpdate() { return lastUpdate; }
    public void setLastUpdate(Instant lastUpdate) { this.lastUpdate = lastUpdate; }
}