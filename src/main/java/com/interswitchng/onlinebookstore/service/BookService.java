package com.interswitchng.onlinebookstore.service;

import com.interswitchng.onlinebookstore.dto.BookRequest;
import com.interswitchng.onlinebookstore.dto.BookResponse;
import com.interswitchng.onlinebookstore.dto.BookSearchParams;
import com.interswitchng.onlinebookstore.model.Book;
import com.interswitchng.onlinebookstore.model.Page;

public interface BookService {

  Book create(BookRequest request,boolean existingAuthor);
  Page<BookResponse> searchBooks(BookSearchParams params);

  BookResponse retrieveByBookId( Integer bookId);


  Book updateBookInventory(Book book);
}
