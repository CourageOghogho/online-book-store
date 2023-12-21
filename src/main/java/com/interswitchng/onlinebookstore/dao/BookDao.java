package com.interswitchng.onlinebookstore.dao;

import com.interswitchng.onlinebookstore.dto.BookResponse;
import com.interswitchng.onlinebookstore.dto.BookSearchParams;
import com.interswitchng.onlinebookstore.model.Book;
import com.interswitchng.onlinebookstore.model.Page;

public interface BookDao {
  Book create(Book model);
  BookResponse retrieveBookById(Integer id);
  BookResponse searchBooksByIsbn(String isbn);
  Page<BookResponse> searchBooks(BookSearchParams params);

  void updateBookInventory(Book book);
}
