package com.interswitchng.onlinebookstore.service.impl;

import com.interswitchng.onlinebookstore.dao.impl.GenreDao;
import com.interswitchng.onlinebookstore.dto.BaseResponse;
import com.interswitchng.onlinebookstore.dto.GenreRequest;
import com.interswitchng.onlinebookstore.exceptions.ServiceLayerException;
import com.interswitchng.onlinebookstore.model.Genre;
import com.interswitchng.onlinebookstore.model.OnlineBookStoreResponseCode;
import com.interswitchng.onlinebookstore.service.GenreService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

  private final GenreDao genreDao;

  private final Logger logger = LoggerFactory.getLogger(GenreServiceImpl.class);

  @Override
  public BaseResponse createGenre(GenreRequest request) {
    var model= Genre.builder()
        .name(request.getGenreName())
        .build();

    logger.info("Creating new genre with name "+request.getGenreName());
    var response=genreDao.create(model);

    if(response==null) throw new ServiceLayerException("Something went wrong, couldn't create genre");

    return new BaseResponse(OnlineBookStoreResponseCode.SUCCESS.getCode(), response.getName());
  }

  @Override
  public Genre retrieveGenreById(Integer genreId) {
    var response=genreDao.retrieveGenreById(genreId);

    if(response==null) throw new ServiceLayerException("Something went wrong, couldn't retrieve Genre");
    return response;
  }

  @Override
  public List<Genre> retrieveAllGenres() {
    var response=genreDao.getAllGenres();

    if(response==null) throw new ServiceLayerException("Something went wrong, couldn't retrieve Genres");
    return response;
  }
}
