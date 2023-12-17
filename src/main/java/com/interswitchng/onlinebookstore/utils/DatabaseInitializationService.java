//package com.interswitchng.onlinebookstore.utils;
//
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
//
//  @PostConstruct
//  public void initializeDb(){
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
//    var genreEntities=genreDao.getAllAuthors();
//
//    BookRequest request1=new BookRequest();
//    request1.setTitle(BookUtil.bookTitleValidator("Clean Code: A Handbook of Agile Software Craftsmanship"));
//    request1.setYearOfPublication(2008);
//    request1.setAuthorName("Robert C. Martin");
//    request1.setGenreId(genreEntities.get(0).getId());
//    request1.setIsbn(BookUtil.bookIsbnValidator("978-0-13-235088-4"));
//    var book1 = bookService.create(request1,false);
//
//
//    BookRequest request2=new BookRequest();
//    request2.setTitle(BookUtil.bookTitleValidator("The Pragmatic Programmer: Your Journey to Mastery"));
//    request2.setYearOfPublication(1999);
//    request2.setAuthorName("Dave Thomas");
//    request2.setGenreId(genreEntities.get(1).getId());
//    request2.setIsbn(BookUtil.bookIsbnValidator("978-0-13-595705-9"));
//    var book2 = bookService.create(request1,false);
//
//
//    BookRequest request3=new BookRequest();
//    request3.setTitle(BookUtil.bookTitleValidator("Design Patterns: Elements of Reusable Object-Oriented Software"));
//    request3.setYearOfPublication(1994);
//    request3.setAuthorName("Erich Gamma");
//    request3.setGenreId(genreEntities.get(1).getId());
//    request3.setIsbn(BookUtil.bookIsbnValidator("978-0-201-63361-0"));
//    var book3 = bookService.create(request1,false);
//
//  }
//
//
//
//}
