package com.interswitchng.onlinebookstore.dao.util;

import com.interswitchng.onlinebookstore.dto.BookResponse;
import com.interswitchng.onlinebookstore.model.Author;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class AuthorRowMapper implements RowMapper<Author> {

  @Override
  public Author mapRow(ResultSet rs, int rowNum) throws SQLException {

    var author =new Author();

    author.setId(rs.getInt("author_id"));
    author.setName(rs.getString("author_name"));
    author.setCreatedDate(rs.getDate("date_created")!= null ? new Date(
        rs.getTimestamp("date_created").getTime()) : null);

    return author;
  }
}
