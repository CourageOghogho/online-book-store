package com.interswitchng.onlinebookstore.service.impl;

import com.interswitchng.onlinebookstore.dao.impl.GenreDao;
import com.interswitchng.onlinebookstore.dto.GenreRequest;
import com.interswitchng.onlinebookstore.exceptions.ServiceLayerException;
import com.interswitchng.onlinebookstore.model.Genre;
import com.interswitchng.onlinebookstore.service.GenreService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

  private final GenreDao genreDao;

  @Override
  public Genre createGenre(GenreRequest request) {
    var model= Genre.builder()
        .name(request.getGenreName())
        .build();

    var response=genreDao.create(model);

    if(response==null) throw new ServiceLayerException("Something went wrong, couldn't create genre");
    return response;
  }

  @Override
  public Genre retrieveGenreById(Integer genreId) {
    var response=genreDao.retrieveGenreById(genreId);

    if(response==null) throw new ServiceLayerException("Something went wrong, couldn't retrieve Genre");
    return response;
  }

  @Override
  public List<Genre> retrieveAllGenres() {
    var response=genreDao.getAllAuthors();

    if(response==null) throw new ServiceLayerException("Something went wrong, couldn't retrieve Genres");
    return response;
  }
}
