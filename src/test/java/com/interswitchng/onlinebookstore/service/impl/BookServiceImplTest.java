package com.interswitchng.onlinebookstore.service.impl;


import com.interswitchng.onlinebookstore.dao.BookDao;
import com.interswitchng.onlinebookstore.dto.*;
import com.interswitchng.onlinebookstore.model.*;
import com.interswitchng.onlinebookstore.service.AuthorService;
import com.interswitchng.onlinebookstore.service.BookService;
import com.interswitchng.onlinebookstore.service.GenreService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import org.slf4j.Logger;


class BookServiceImplTest {
  private BookDao bookDao;
  private AuthorService authorService;
  private GenreService genreService;
  private Logger logger;
  private BookService bookService;

  @BeforeEach
  void setUp() {
    bookDao = Mockito.mock(BookDao.class);
    authorService = Mockito.mock(AuthorService.class);
    genreService = Mockito.mock(GenreService.class);
    logger = Mockito.mock(Logger.class);
    bookService = new BookServiceImpl(bookDao, authorService,genreService);
  }

  @Test
  void create() {
    BookRequest request = new BookRequest();
    request.setIsbn("123456789");
    request.setAuthorName("TestAuthor");
    request.setGenreId(1);
    request.setYearOfPublication(2022);
    request.setTitle("TestTitle");

    AuthorRequest authorRequest = new AuthorRequest();
    authorRequest.setAuthorName("TestAuthor");

    Author author = new Author();
    author.setId(1);
    author.setName("TestAuthor");

    Genre genre = new Genre();
    genre.setId(1);
    genre.setName("TestGenre");

    when(bookDao.searchBooksByIsbn("123456789")).thenReturn(null);
    when(authorService.createAuthor(authorRequest)).thenReturn(author);
    when(genreService.retrieveGenreById(1)).thenReturn(genre);

    Book createdBook = new Book();
    createdBook.setId(1);
    createdBook.setIsbn("123456789");
    createdBook.setAuthorId(1);
    createdBook.setGenreId(1);
    createdBook.setYearOfPublication(2022);
    createdBook.setTitle("TestTitle");

    when(bookDao.create(Mockito.any(Book.class))).thenReturn(createdBook);
    Book result = bookService.create(request, false);

    assertNotNull(result);
    assertEquals(1, result.getId());
    assertEquals("123456789", result.getIsbn());
    assertEquals(1, result.getAuthorId());
    assertEquals(1, result.getGenreId());
    assertEquals(2022, result.getYearOfPublication());
    assertEquals("TestTitle", result.getTitle());
  }

  @Test
  void searchBooks() {
    BookSearchParams params = new BookSearchParams();
    params.setIsbn("123456789");

    BookResponse foundBook = new BookResponse();
    foundBook.setId(1);
    foundBook.setIsbn("123456789");
    foundBook.setAuthorId(1);
    foundBook.setGenreId(1);
    foundBook.setYearOfPublication(2022);
    foundBook.setTitle("TestTitle");

    when(bookDao.searchBooksByIsbn("123456789")).thenReturn(foundBook);
    Page<BookResponse> result = bookService.searchBooks(params);

    assertNotNull(result);
    assertEquals(1L, result.getCount());
    assertEquals(1, result.getContent().size());
    assertEquals("123456789", result.getContent().get(0).getIsbn());
  }

  @Test
  void retrieveByBookId() {
    Integer bookId = 1;

    BookResponse expectedBookResponse = new BookResponse();
    expectedBookResponse.setId(1);
    expectedBookResponse.setIsbn("123456789");
    expectedBookResponse.setAuthorId(1);
    expectedBookResponse.setGenreId(1);
    expectedBookResponse.setYearOfPublication(2022);
    expectedBookResponse.setTitle("TestTitle");

    when(bookDao.retrieveBookById(bookId)).thenReturn(expectedBookResponse);
    BookResponse result = bookService.retrieveByBookId(bookId);

    assertNotNull(result);
    assertEquals(1, result.getId());
    assertEquals("123456789", result.getIsbn());
    assertEquals(1, result.getAuthorId());
    assertEquals(1, result.getGenreId());
    assertEquals(2022, result.getYearOfPublication());
    assertEquals("TestTitle", result.getTitle());
  }
//
//  @Test
//  void updateBookInventory() {
//    // Arrange
//    BookResponse book = new BookResponse();
//    book.setId(1);
//    book.setIsbn("123456789");
//    book.setAuthorId(1);
//    book.setGenreId(1);
//    book.setYearOfPublication(2022);
//    book.setTitle("TestTitle");
//
//    when(bookDao.updateBookInventory(Mockito.any(Book.class))).thenReturn(book);
//
//    // Act
//    Book result = bookService.updateBookInventory(book);
//
//    // Assert
//    assertNotNull(result);
//    assertEquals(1, result.getId());
//    assertEquals("123456789", result.getIsbn());
//    assertEquals(1, result.getAuthorId());
//    assertEquals(1, result.getGenreId());
//    assertEquals(2022, result.getYearOfPublication());
//    assertEquals("TestTitle", result.getTitle());
//  }
}
