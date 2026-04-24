package org.iti.rest.service;

import jakarta.persistence.EntityManager;
import org.iti.rest.config.JPAUtil;
import org.iti.rest.dao.FilmDao;
import org.iti.rest.dto.AddActorToFilmRequest;
import org.iti.rest.dto.CreateFilmRequest;
import org.iti.rest.dto.ReturnFilm;
import org.iti.rest.dto.ReturnFilmWithLanguage;
import org.iti.rest.entity.Film;
import org.iti.rest.entity.Language;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class FilmService {
  private FilmDao filmDao;
  public FilmService(FilmDao filmDao)
  {
      this.filmDao=filmDao;
  }

    public ReturnFilm create(CreateFilmRequest film) {
        Film saveFilm = new Film();
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();

        try {
            Language l = em.find(Language.class, film.getLanguageId());
            if(l == null)
                throw new RuntimeException("Language not found");

            // Set basic fields
            saveFilm.setDescription(film.getDescription());
            saveFilm.setReleaseYear(film.getReleaseYear());
            saveFilm.setTitle(film.getTitle());
            saveFilm.setLanguage(l);

            // CRITICAL: Set rentalRate from request
            if (film.getRentalRate() != null) {
                saveFilm.setRentalRate(film.getRentalRate());
            } else {
                saveFilm.setRentalRate(new BigDecimal("4.99")); // Default value
            }

            // Set missing required fields with defaults
            saveFilm.setRentalDuration((short) 3);  // Default rental duration
            saveFilm.setReplacementCost(new BigDecimal("19.99")); // Default replacement cost
            saveFilm.setRating("G"); // Default rating
            saveFilm.setLastUpdate(Instant.now()); // Set current timestamp

            // Optional: Set other fields if needed
            saveFilm.setLength((short) 120); // Default length
            saveFilm.setSpecialFeatures("Trailers"); // Default special features

            Film createdFilm = filmDao.save(saveFilm);

            ReturnFilm returned = new ReturnFilm();
            returned.setDescription(createdFilm.getDescription());
            returned.setTitle(createdFilm.getTitle());
            returned.setId(createdFilm.getFilmId());

            return returned;

        } finally {
            em.close();
        }
    }
        public ReturnFilm findById(Short id) {
           Film film= filmDao.findById(id).orElseThrow(()-> new RuntimeException("Film not found "));
           ReturnFilm returnFilm = new ReturnFilm();
           returnFilm.setDescription(film.getDescription());
           returnFilm.setTitle(film.getTitle());
           return returnFilm;
        }
        public List<ReturnFilmWithLanguage> findAll(){
           List<Film> films = filmDao.findAll();
            List<ReturnFilmWithLanguage> returnedFilms =new ArrayList<>();
           for( Film film :films) {
               ReturnFilmWithLanguage returned = new ReturnFilmWithLanguage();
               returned.setLanguage(film.getLanguage().getName());
               returned.setRating(film.getRating());
               returned.setDescription(film.getDescription());
               returned.setReleaseYear(film.getReleaseYear());
               returned.setTitle(film.getTitle());
               returnedFilms.add(returned);
           }
            return returnedFilms;
        }
        public void delete(Short id) {
           Film film=filmDao.findById(id).orElseThrow(()->new RuntimeException("Film not found"));
           filmDao.delete(id);
        }
        public ReturnFilmWithLanguage findWithLanguage(Short id)
        {
         Film film= filmDao.findWithLanguage(id);
         ReturnFilmWithLanguage returned = new ReturnFilmWithLanguage();
         returned.setLanguage(film.getLanguage().getName());
         returned.setRating(film.getRating());
         returned.setDescription(film.getDescription());
         returned.setReleaseYear(film.getReleaseYear());
         returned.setTitle(film.getTitle());
            return returned;
        }
        public void addActorToFilm(AddActorToFilmRequest request)
         {
                 filmDao.addActorToFilm(request);
         }
    }

