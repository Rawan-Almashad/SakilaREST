package org.iti.rest.service;

import org.iti.rest.dao.CityDao;
import org.iti.rest.dto.CreateCityRequest;
import org.iti.rest.dto.UpdateCityRequest;
import org.iti.rest.entity.City;
import org.iti.rest.entity.Country;

import java.time.Instant;
import java.util.List;

public class CityService {

    private final CityDao cityDao;
    private final CountryService countryService;

    public CityService(CityDao cityDao, CountryService countryService) {
        this.cityDao = cityDao;
        this.countryService = countryService;
    }

    public City findById(Short id) {
        return cityDao.findById(id)
                .orElseThrow(() -> new RuntimeException("City not found"));
    }

    public List<City> findAll() {
        return cityDao.findAll();
    }

    public CreateCityRequest save(CreateCityRequest request) {

        Country country = countryService.findById(request.getCountryId());

        City city = new City();
        city.setCity(request.getCity());
        city.setCountry(country);
        city.setLastUpdate(Instant.now());

        return cityDao.save(city);
    }

    public UpdateCityRequest update(UpdateCityRequest request) {

        Country country = countryService.findById(request.getCountryId());

        City city = new City();
        city.setCity(request.getCity());
        city.setCountry(country);
        city.setLastUpdate(Instant.now());
        city.setId(request.getId());

        return cityDao.update(city);
    }

    public boolean deleteById(Short id) {
        return cityDao.deleteById(id);
    }
}