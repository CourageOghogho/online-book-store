package com.interswitchng.onlinebookstore.service.impl;

import com.interswitchng.onlinebookstore.dao.impl.GenreDao;
import com.interswitchng.onlinebookstore.dto.BaseResponse;
import com.interswitchng.onlinebookstore.dto.GenreRequest;
import com.interswitchng.onlinebookstore.model.Genre;
import com.interswitchng.onlinebookstore.model.OnlineBookStoreResponseCode;
import com.interswitchng.onlinebookstore.service.GenreService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.slf4j.Logger;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class GenreServiceImplTest {

  private GenreDao genreDao;

  @Mock
  private Logger logger;

  private GenreService genreService;

  @BeforeEach
  void setUp() {

    genreDao = Mockito.mock(GenreDao.class);
    genreService = new GenreServiceImpl(genreDao);


  }

  @Test
  void createGenre() {
    // Arrange
    GenreRequest request = new GenreRequest();
    request.setGenreName("TestGenre");

    Integer genreId = 1;
    var createdGenre=new Genre();
    createdGenre.setName("TestGenre"); createdGenre.setId(genreId);
    when(genreDao.create(Mockito.any(Genre.class))).thenReturn(createdGenre);
    BaseResponse response = genreService.createGenre(request);
    assertEquals(OnlineBookStoreResponseCode.SUCCESS.getCode(), response.getResponseCode());
    assertEquals("TestGenre", response.getResponseMessage());
  }

  @Test
  void retrieveGenreById() {
    Integer genreId = 1;

    var retrievedGenre=new Genre();
    retrievedGenre.setName("TestGenre"); retrievedGenre.setId(genreId);

    when(genreDao.retrieveGenreById(genreId)).thenReturn(retrievedGenre);
    Genre result = genreService.retrieveGenreById(genreId);
    assertNotNull(result);
    assertEquals("TestGenre", result.getName());
  }

  @Test
  void retrieveAllGenres() {

    var genre1=new Genre();
    genre1.setName("Genre1"); genre1.setId(1);
    var genre2=new Genre();
    genre2.setName("Genre2"); genre2.setId(2);

    List<Genre> allGenres = Arrays.asList(genre1,genre2);

    when(genreDao.getAllGenres()).thenReturn(allGenres);
    List<Genre> result = genreService.retrieveAllGenres();
    assertNotNull(result);
    assertEquals(2, result.size());
  }
}
