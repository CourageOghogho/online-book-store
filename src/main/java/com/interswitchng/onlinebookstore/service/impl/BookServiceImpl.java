package com.interswitchng.onlinebookstore.service.impl;

import com.interswitchng.onlinebookstore.dao.BookDao;
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
import com.interswitchng.onlinebookstore.utils.OnlineBookStoreResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
  private final BookDao bookDao;
  private final AuthorService authorService;
  private final GenreService genreService;


  @Override
  public Book create(BookRequest request, boolean existingAuthor) {

    Author  author;
    if (existingAuthor) {
      author = authorService.retrieveAuthorById(request.getAuthorId());
    }else {
      author= Author.builder().name(request.getAuthorName()).build();
    }

    Book model= new Book();

    model.setGenreId(genreService.retrieveGenreById(request.getGenreId()).getId());
    model.setAuthorId(author.getId());
    model.setIsbn(request.getIsbn());
    model.setTitle(request.getTitle());

    var createdEntity=bookDao.create(model);
    if (createdEntity!=null) return createdEntity;
    throw new ServiceLayerException("Couldn't create book inventory");
  }

  @Override
  public Page<BookResponse> searchBooks(BookSearchParams params) {
    if(params.getIsbn()!=null){
        return bookDao.searchBooksByIsbn(params);
    }
    return bookDao.searchBooks(params);
  }

  @Override
  public BookResponse retrieveByBookId(Integer bookId) {

    if(bookId<1) throw new BadRequestException("INVALID ID", "Please provide a valid book id");
    var response=bookDao.retrieveBookById(bookId);
    if (response==null)  throw new NotFoundException(OnlineBookStoreResponseCode.ENTITY_NOT_FOUND.getCode(),
        "Records not found");
    return response;
  }



}
