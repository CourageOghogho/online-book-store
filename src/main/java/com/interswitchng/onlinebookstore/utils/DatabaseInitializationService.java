//package com.interswitchng.onlinebookstore.utils;
//
//import com.interswitchng.onlinebookstore.dao.BookDao;
//import com.interswitchng.onlinebookstore.dao.impl.AuthorDao;
//import com.interswitchng.onlinebookstore.dao.impl.GenreDao;
//import com.interswitchng.onlinebookstore.dto.BookRequest;
//import com.interswitchng.onlinebookstore.model.Genre;
//import com.interswitchng.onlinebookstore.service.BookService;
//import jakarta.annotation.PostConstruct;
//import java.util.ArrayList;
//import java.util.List;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class DatabaseInitializationService {
//
//  private final AuthorDao authorDao;
//  private final GenreDao genreDao;
//  private final BookService bookService;
//  private final BookDao bookDao;
//
//  @PostConstruct
//  public void initializeDb(){
//
//
//    var genreFriction= Genre.builder().name("Friction").build();
//    var genreThriller= Genre.builder().name("Thriller").build();
//    var genreMystery= Genre.builder().name("Mystery").build();
//    var genrePoetry= Genre.builder().name("Poetry").build();
//    var genreHorror= Genre.builder().name("Horror").build();
//    var genreSatire= Genre.builder().name("Satire").build();
//
//    List<Genre> genres=new ArrayList<>();
//    genres.add(genreFriction);
//    genres.add(genreThriller);
//    genres.add(genreMystery);
//    genres.add(genrePoetry);
//    genres.add(genreHorror);
//    genres.add(genreSatire);
//    genres.forEach(genreDao::create);
//
//    var genreEntities=genreDao.getAllGenres();
//
//    BookRequest request1=new BookRequest();
//    request1.setTitle(BookUtil.bookTitleValidator("CleanCode"));
//    request1.setYearOfPublication(2008);
//    request1.setAuthorName("Robert C. Martin");
//    var genreInput =genreEntities.get(0);
//    request1.setGenreId(genreInput.getId());
//
//    request1.setIsbn(BookUtil.bookIsbnValidator("978-0-13-235088-4"));
//
//    var bookModel=bookDao.searchBooksByIsbn(request1.getIsbn());
//    if (bookModel!=null){
//      var book1 = bookService.create(request1,false);
//
//    }
//
//
//    BookRequest request2=new BookRequest();
//    request2.setTitle(BookUtil.bookTitleValidator("ThePragmaticProgrammer"));
//    request2.setYearOfPublication(1999);
//    request2.setAuthorName("Dave Thomas");
//    request2.setGenreId(genreEntities.get(1).getId());
//    request2.setIsbn(BookUtil.bookIsbnValidator("978-0-13-595705-9"));
//
//    var bookModel2=bookDao.searchBooksByIsbn(request2.getIsbn());
//    if (bookModel2!=null){
//      bookService.create(request2,false);
//
//    }
//
//
//    BookRequest request3=new BookRequest();
//    request3.setTitle(BookUtil.bookTitleValidator("DesignPatterns"));
//    request3.setYearOfPublication(1994);
//    request3.setAuthorName("Erich Gamma");
//    request3.setGenreId(genreEntities.get(1).getId());
//    request3.setIsbn(BookUtil.bookIsbnValidator("978-0-201-63361-0"));
//
//    var bookModel3=bookDao.searchBooksByIsbn(request3.getIsbn());
//    if (bookModel3!=null){
//      bookService.create(request3,false);
//
//    }
//  }
//
//
//}
