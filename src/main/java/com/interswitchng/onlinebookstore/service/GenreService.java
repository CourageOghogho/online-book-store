package com.interswitchng.onlinebookstore.service;

import com.interswitchng.onlinebookstore.dto.BaseResponse;
import com.interswitchng.onlinebookstore.dto.GenreRequest;
import com.interswitchng.onlinebookstore.model.Genre;
import java.util.List;

public interface GenreService {

  BaseResponse createGenre(GenreRequest request);

  Genre retrieveGenreById(Integer genreId);

  List<Genre> retrieveAllGenres();
}
