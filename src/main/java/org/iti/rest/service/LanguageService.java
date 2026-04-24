package org.iti.rest.service;

import org.iti.rest.dao.LanguageDao;
import org.iti.rest.dto.CreateLanguageRequest;
import org.iti.rest.dto.LanguageResponse;
import org.iti.rest.dto.UpdateLanguageRequest;
import org.iti.rest.entity.Language;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

public class LanguageService {
    private final LanguageDao languageDao;

    public LanguageService(LanguageDao languageDao) {
        this.languageDao = languageDao;
    }

    public LanguageResponse getById(Short id) {
        Language language = languageDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Language not found with id: " + id));
        return convertToResponse(language);
    }

    public List<LanguageResponse> getAll() {
        List<Language> languages = languageDao.findAll();
        return languages.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public LanguageResponse create(CreateLanguageRequest request) {
        Language language = new Language();
        language.setName(request.getName());
        language.setLastUpdate(Instant.now());

        Language savedLanguage = languageDao.save(language);
        return convertToResponse(savedLanguage);
    }

    public LanguageResponse update(UpdateLanguageRequest request) {
        // First check if language exists
        Language existingLanguage = languageDao.findById(request.getId())
                .orElseThrow(() -> new RuntimeException("Language not found with id: " + request.getId()));

        // Update fields
        existingLanguage.setName(request.getName());
        existingLanguage.setLastUpdate(Instant.now());

        Language updatedLanguage = languageDao.update(existingLanguage);
        return convertToResponse(updatedLanguage);
    }

    public boolean delete(Short id) {
        // Check if language exists
        languageDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Language not found with id: " + id));
        return languageDao.deleteById(id);
    }

    // Helper method to convert Entity to Response DTO
    private LanguageResponse convertToResponse(Language language) {
        LanguageResponse response = new LanguageResponse();
        response.setId(language.getLanguageId());
        response.setName(language.getName());

        // Convert Instant to String
        if (language.getLastUpdate() != null) {
            response.setLastUpdate(language.getLastUpdate().toString());
        }

        return response;
    }
}