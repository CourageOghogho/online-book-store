package com.interswitchng.onlinebookstore.service.impl;

import com.interswitchng.onlinebookstore.dao.BookDao;
import com.interswitchng.onlinebookstore.dto.AuthorRequest;
import com.interswitchng.onlinebookstore.dto.BaseResponse;
import com.interswitchng.onlinebookstore.dto.BookRequest;
import com.interswitchng.onlinebookstore.dto.BookResponse;
import com.interswitchng.onlinebookstore.dto.BookSearchParams;
import com.interswitchng.onlinebookstore.exceptions.BadRequestException;
import com.interswitchng.onlinebookstore.exceptions.NotFoundException;
import com.interswitchng.onlinebookstore.exceptions.ServiceLayerException;
import com.interswitchng.onlinebookstore.model.Author;
import com.interswitchng.onlinebookstore.model.Book;
import com.interswitchng.onlinebookstore.model.Page;
import com.interswitchng.onlinebookstore.service.AuthorService;
import com.interswitchng.onlinebookstore.service.BookService;
import com.interswitchng.onlinebookstore.service.GenreService;
import com.interswitchng.onlinebookstore.utils.CustomModelMapper;
import com.interswitchng.onlinebookstore.utils.OnlineBookStoreResponseCode;
import java.util.Arrays;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
  private final BookDao bookDao;
  private final AuthorService authorService;
  private final GenreService genreService;
  private final Logger logger = LoggerFactory.getLogger(BookServiceImpl.class);


  @Override
  public Book create(BookRequest request, boolean existingAuthor) {
    var book=bookDao.searchBooksByIsbn(request.getIsbn());
    logger.info("Book with isbn: "+request.getIsbn()+" already exists");
    if(book!=null) throw new ServiceLayerException("ISBN already exists");

    Author  author;
    if (existingAuthor) {
      author = authorService.retrieveAuthorById(request.getAuthorId());
    }else {
      var authorRequest=new AuthorRequest();
      authorRequest.setAuthorName(request.getAuthorName());
      author=authorService.createAuthor(authorRequest);
    }

    Book model= new Book();

    var genre=genreService.retrieveGenreById(request.getGenreId());
    model.setGenreId(request.getGenreId());
    model.setAuthorId(author.getId());
    model.setIsbn(request.getIsbn());
    model.setYearOfPublication(request.getYearOfPublication());
    model.setTitle(request.getTitle());

    var createdEntity=bookDao.create(model);
    if (createdEntity!=null) {
      return createdEntity;
    }
    throw new ServiceLayerException("Couldn't create book inventory");
  }

  @Override
  public Page<BookResponse> searchBooks(BookSearchParams params) {
    if(params.getIsbn()!=null){
        var book= bookDao.searchBooksByIsbn(params.getIsbn());
        return new Page<>(1L, Collections.singletonList(book));
    }
    return bookDao.searchBooks(params);
  }

  @Override
  public BookResponse retrieveByBookId(Integer bookId) {

    if(bookId<1) throw new BadRequestException("INVALID ID", "Please provide a valid book id");
    var response=bookDao.retrieveBookById(bookId);
    if (response==null) {
      logger.info("Invalid book id");
      throw new NotFoundException(OnlineBookStoreResponseCode.ENTITY_NOT_FOUND.getCode(),
          "Records not found");
    }
    return response;
  }

  @Override
  public Book updateBookInventory(Book book) {
     bookDao.updateBookInventory(book);
     return CustomModelMapper.BookOfBookResponse(bookDao.retrieveBookById(book.getId()));
  }

}
