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
    BookResponse book = new BookResponse();

    book.setId(rs.getInt("book_id"));
    book.setTitle(rs.getString("title"));
    book.setIsbn(rs.getString("isbn"));
    book.setYearOfPublication(rs.getInt("year_of_publication"));
    book.setAvailableCount(rs.getInt("available_book_count"));
    book.setCreatedDate(rs.getTimestamp("date_created") != null ? new Date(
        rs.getTimestamp("date_created").getTime()) : null);
    book.setGenreId(rs.getInt("genre_id"));
    book.setGenreName(rs.getString("genre_name"));
    book.setAuthorId(rs.getInt("author_id"));
    book.setAuthorName(rs.getString("author_name"));
    book.setPrice(rs.getBigDecimal("price"));


    return book;
  }
}
