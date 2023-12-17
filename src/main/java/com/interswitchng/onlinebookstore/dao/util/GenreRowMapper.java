package com.interswitchng.onlinebookstore.dao.util;

import com.interswitchng.onlinebookstore.model.Genre;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class GenreRowMapper  implements RowMapper<Genre> {

  @Override
  public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {


    var genre =new Genre();

    genre.setId(rs.getInt("genre_id"));
    genre.setName(rs.getString("genre_name"));
    genre.setCreatedDate(rs.getDate("date_created")!= null ? new Date(
        rs.getTimestamp("date_created").getTime()) : null);


    return genre;
  }
}