package org.iti.rest.service;

import org.iti.rest.dao.LanguageDao;
import org.iti.rest.entity.Language;

import java.util.List;
import java.util.Optional;

public class LanguageService
{
    private LanguageDao languageDao;
    public LanguageService(LanguageDao languageDao)
    {
        this.languageDao=languageDao;
    }
    public Optional<Language> getById(Short id) {
        return languageDao.findById(id);
    }

    public List<Language> getAll() {
        return languageDao.findAll();
    }

    public Language create(Language language) {
        return languageDao.save(language);
    }

    public Language update(Language language) {
        return languageDao.update(language);
    }

    public boolean delete(Short id) {
        return languageDao.deleteById(id);
    }
}
