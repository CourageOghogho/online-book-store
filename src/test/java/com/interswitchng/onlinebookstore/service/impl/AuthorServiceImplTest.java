package com.interswitchng.onlinebookstore.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import com.interswitchng.onlinebookstore.dao.impl.AuthorDao;
import com.interswitchng.onlinebookstore.dto.AuthorRequest;
import com.interswitchng.onlinebookstore.exceptions.ServiceLayerException;
import com.interswitchng.onlinebookstore.model.Author;
import com.interswitchng.onlinebookstore.service.impl.AuthorServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class AuthorServiceImplTest {

  @Mock
  private AuthorDao authorDao;

  @InjectMocks
  private AuthorServiceImpl authorService;

  @BeforeEach
  void setUp() {
    authorDao = Mockito.mock(AuthorDao.class);
    authorService = new AuthorServiceImpl(authorDao);
  }

  @Test
  void createAuthor() {
    // Arrange
    AuthorRequest request = new AuthorRequest();
    request.setAuthorName("TestAuthor");


    Author createdAuthor = new Author();
    createdAuthor.setName("TestAuthor"); createdAuthor.setId(1);


    when(authorDao.create(Mockito.any(Author.class))).thenReturn(createdAuthor);
    Author result = authorService.createAuthor(request);

    assertNotNull(result);
    assertEquals(1, result.getId());
    assertEquals("TestAuthor", result.getName());
  }

  @Test
  void retrieveAuthorById() {
    Integer authorId = 1;
    Author expectedAuthor = new Author();
    expectedAuthor.setName("TestAuthor"); expectedAuthor.setId(authorId);
    when(authorDao.retrieveAuthorById(authorId)).thenReturn(expectedAuthor);

    Author result = authorService.retrieveAuthorById(authorId);
    assertNotNull(result);
    assertEquals(1, result.getId());
    assertEquals("TestAuthor", result.getName());
  }

  @Test
  void retrieveAllAuthor() {
    Author author1 = new Author();
    author1.setName("Author1"); author1.setId(1);

    Author author2 = new Author();
    author2.setName("Author2"); author2.setId(2);

    List<Author> expectedAuthors = Arrays.asList(author1, author2);

    when(authorDao.getAllAuthors()).thenReturn(expectedAuthors);
    List<Author> result = authorService.retrieveAllAuthor();

    assertNotNull(result);
    assertEquals(2, result.size());
    assertEquals("Author1", result.get(0).getName());
    assertEquals("Author2", result.get(1).getName());
  }
}
