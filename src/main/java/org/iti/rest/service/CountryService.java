package org.iti.rest.service;

import org.iti.rest.dao.CountryDao;
import org.iti.rest.dto.CreateCountry;
import org.iti.rest.dto.UpdateCountryRequest;
import org.iti.rest.entity.Country;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

public class CountryService {
    private CountryDao countryDao;

    public CountryService(CountryDao countryDao) {
        this.countryDao = countryDao;
    }

    public CreateCountry findById(Short id) throws RuntimeException {
        Country country = countryDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Country not found"));

        CreateCountry response = new CreateCountry();
        response.setCountry(country.getCountry());
        response.setCountry(country.getCountry());
        return response;
    }

    public List<CreateCountry> findAll() {
        List<Country> countries = countryDao.findAll();
        return countries.stream()
                .map(country -> {
                    CreateCountry dto = new CreateCountry();
                    dto.setCountry(country.getCountry());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public CreateCountry save(CreateCountry request) {
        Country country = new Country();
        country.setLastUpdate(Instant.now());
        country.setCountry(request.getCountry());

        CreateCountry savedCountry = countryDao.save(country);

        CreateCountry response = new CreateCountry();
        response.setCountry(savedCountry.getCountry());
        return response;
    }

    public CreateCountry update(UpdateCountryRequest request) {
        // Check if country exists
        Country existing = countryDao.findById(request.getId())
                .orElseThrow(() -> new RuntimeException("Country not found"));

        // Update the country
        existing.setCountry(request.getCountry());
        existing.setLastUpdate(Instant.now());

        CreateCountry updatedCountry = countryDao.update(existing);

        CreateCountry response = new CreateCountry();
        response.setCountry(updatedCountry.getCountry());
        return response;
    }

    public boolean deleteById(Short id) {
        return countryDao.deleteById(id);
    }
}