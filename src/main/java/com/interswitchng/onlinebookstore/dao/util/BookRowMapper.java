package com.interswitchng.onlinebookstore.dao.util;

import com.interswitchng.onlinebookstore.dto.BookResponse;
import com.interswitchng.onlinebookstore.model.Author;
import com.interswitchng.onlinebookstore.model.Genre;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class BookRowMapper implements RowMapper<BookResponse> {

  @Override
  public BookResponse mapRow(ResultSet rs, int rowNum) throws SQLException {

    BookResponse book =new BookResponse();

    Genre genre=new Genre();
    genre.setId(rs.getInt("genre_id"));
    genre.setName(rs.getString("genre_name"));
    genre.setCreatedDate(rs.getTimestamp("date_created") != null ? new Date(
        rs.getTimestamp("date_created").getTime()) : null);

    Author author=new Author();
    author.setId(rs.getInt("author_id"));
    author.setName(rs.getString("author_name"));

    book.setId(rs.getInt("book_id"));
    book.setTitle(rs.getString("title"));
    book.setIsbn(rs.getString("isbn"));
    book.setYearOfPublication(rs.getInt("year_of_publication"));
    book.setAvailableCount(rs.getInt("available_book_count"));
    book.setAuthor(author);
    book.setGenre(genre);

    return book;
  }
}