package org.iti.rest.service;

import org.iti.rest.dao.CityDao;
import org.iti.rest.dto.CityResponse;
import org.iti.rest.dto.CreateCityRequest;
import org.iti.rest.dto.UpdateCityRequest;
import org.iti.rest.entity.City;
import org.iti.rest.entity.Country;
import jakarta.persistence.EntityManager;
import org.iti.rest.config.JPAUtil;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

public class CityService {

    private final CityDao cityDao;

    public CityService(CityDao cityDao) {
        this.cityDao = cityDao;
    }

    public CityResponse findById(Short id) {
        City city = cityDao.findById(id)
                .orElseThrow(() -> new RuntimeException("City not found"));
        return convertToResponse(city);
    }

    public List<CityResponse> findAll() {
        List<City> cities = cityDao.findAll();
        return cities.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public CityResponse save(CreateCityRequest request) {  // Changed to return CityResponse
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            Country country = em.find(Country.class, request.getCountryId());
            if (country == null) {
                throw new RuntimeException("Country not found with id: " + request.getCountryId());
            }

            City city = new City();
            city.setCity(request.getCity());
            city.setCountry(country);
            city.setLastUpdate(Instant.now());

            City savedCity = cityDao.save(city);
            return convertToResponse(savedCity);
        } finally {
            em.close();
        }
    }

    public CityResponse update(UpdateCityRequest request) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            // Verify city exists
            City existingCity = cityDao.findById(request.getId())
                    .orElseThrow(() -> new RuntimeException("City not found with id: " + request.getId()));

            // Find the country
            Country country = em.find(Country.class, request.getCountryId());
            if (country == null) {
                throw new RuntimeException("Country not found with id: " + request.getCountryId());
            }

            // Update existing city
            existingCity.setCity(request.getCity());
            existingCity.setCountry(country);
            existingCity.setLastUpdate(Instant.now());

            City updatedCity = cityDao.update(existingCity);
            return convertToResponse(updatedCity);
        } finally {
            em.close();
        }
    }

    public boolean deleteById(Short id) {
        return cityDao.deleteById(id);
    }

    private CityResponse convertToResponse(City city) {
        CityResponse response = new CityResponse();
        response.setId(city.getId());
        response.setCity(city.getCity());

        if (city.getCountry() != null) {
            response.setCountryId(city.getCountry().getId());
            response.setCountryName(city.getCountry().getCountry());
        }

        // Convert Instant to String
        if (city.getLastUpdate() != null) {
            response.setLastUpdate(city.getLastUpdate().toString());
        }

        return response;
    }
}