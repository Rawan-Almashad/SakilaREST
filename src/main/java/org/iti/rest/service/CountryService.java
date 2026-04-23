package org.iti.rest.service;

import org.iti.rest.dao.CountryDao;
import org.iti.rest.dto.CreateCountry;
import org.iti.rest.dto.UpdateCountryRequest;
import org.iti.rest.entity.Country;

import java.time.Instant;
import java.util.List;

public class CountryService {
    private CountryDao countryDao;
    public CountryService(CountryDao countryDao)
    {
        this.countryDao=countryDao;
    }
    public Country findById(Short id) throws RuntimeException{
        return countryDao.findById(id).orElseThrow(() -> new RuntimeException("Country not found"));
    }

    public List<Country> findAll() {
        return countryDao.findAll();
    }

    public CreateCountry save(CreateCountry request) {
        Country country = new Country();
        country.setLastUpdate(Instant.now());
        country.setCountry(request.getCountry());
        return countryDao.save(country);
    }

    public CreateCountry update(UpdateCountryRequest request) {
        Country country =new Country();
        country.setLastUpdate(Instant.now());
        country.setCountry(request.getCountry());
        country.setId(request.getId());
        return countryDao.update(country);
    }

    public boolean deleteById(Short id) {
        return countryDao.deleteById(id);
    }
}
