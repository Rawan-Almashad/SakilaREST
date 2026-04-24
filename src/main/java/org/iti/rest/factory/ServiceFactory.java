package org.iti.rest.factory;

import org.iti.rest.dao.*;
import org.iti.rest.service.*;

public class ServiceFactory {
    private static final LanguageService languageService =
            new LanguageService(new LanguageDao());
    private static final FilmService filmService =
            new FilmService(new FilmDao());
    private static final ActorService actorService =
            new ActorService(new ActorDao());
    private static final CountryService countryService =
            new CountryService(new CountryDao());
    private static final CityService cityService =
            new CityService(new CityDao());
    private static final StoreService storeService =
            new StoreService(new StoreDao());
    private static final StaffService staffService =
            new StaffService(new StaffDao());
    private static final CategoryService categoryService =
            new CategoryService(new CategoryDao());
    private static final CustomerService customerService =
            new CustomerService(new CustomerDao());

    public static LanguageService getLanguageService() {
        return languageService;
    }
    public static FilmService getFilmService() {
        return filmService;
    }
    public static ActorService getActorService(){return actorService;}
    public static CountryService getCountryService(){return countryService;}
    public static CityService getCityService(){return cityService;}
    public static StoreService getStoreService(){return storeService;}
    public static StaffService getStaffService(){return staffService;}
    public static CategoryService getCategoryService(){return categoryService;}
    public static CustomerService getCustomerService(){return customerService;}

}
