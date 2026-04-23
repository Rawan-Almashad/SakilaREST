package org.iti.rest.service;

import org.iti.rest.dao.FilmDao;
import org.iti.rest.dto.AddActorToFilmRequest;
import org.iti.rest.dto.ReturnFilm;
import org.iti.rest.entity.Film;

import java.util.List;

public class FilmService {
  private FilmDao filmDao;
  public FilmService(FilmDao filmDao)
  {
      this.filmDao=filmDao;
  }

        public Film create(Film film)  {
            return filmDao.save(film);
        }
        public ReturnFilm findById(Short id) {
           Film film= filmDao.findById(id).orElseThrow(()-> new RuntimeException("Film not found "));
           ReturnFilm returnFilm = new ReturnFilm();
           returnFilm.setDescription(film.getDescription());
           returnFilm.setTitle(film.getTitle());
           return returnFilm;
        }
        public List<Film> findAll(){
           return filmDao.findAll();
        }
        public void delete(Short id) {
           Film film=filmDao.findById(id).orElseThrow(()->new RuntimeException("Film not found"));
           filmDao.delete(id);
        }
        public  Film findWithLanguage(Short id)
        {
          return filmDao.findWithLanguage(id);
        }
        public void addActorToFilm(AddActorToFilmRequest request)
         {
                 filmDao.addActorToFilm(request);
         }
    }

