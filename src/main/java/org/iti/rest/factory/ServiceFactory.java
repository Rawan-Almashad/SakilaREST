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
            new CityService(new CityDao(),countryService);

    public static LanguageService getLanguageService() {
        return languageService;
    }
    public static FilmService getFilmService() {
        return filmService;
    }
    public static ActorService getActorService(){return actorService;}
    public static CountryService getCountryService(){return countryService;}
    public static CityService getCityService(){return cityService;}
}
