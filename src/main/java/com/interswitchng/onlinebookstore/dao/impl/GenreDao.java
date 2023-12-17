package com.interswitchng.onlinebookstore.dao.impl;

import com.interswitchng.onlinebookstore.dao.BaseDao;
import com.interswitchng.onlinebookstore.dao.util.BookRowMapper;
import com.interswitchng.onlinebookstore.model.Author;
import com.interswitchng.onlinebookstore.model.Book;
import com.interswitchng.onlinebookstore.model.Genre;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

@Repository
public class GenreDao extends BaseDao<Genre> {


  @Autowired
  @Override
  public void setDataSource(@Qualifier(value = "onlineBookStoreDs") DataSource dataSource) {
    this.jdbcTemplate = new JdbcTemplate(dataSource);

    createJdbcCall = new SimpleJdbcCall(jdbcTemplate)
        .withProcedureName("psp_create_genre")
        .returningResultSet(SINGLE_RESULT, BeanPropertyRowMapper.newInstance(Book.class));

    retrieveOneJdbcCall = new SimpleJdbcCall(jdbcTemplate)
        .withProcedureName("psp_retrieve_genre_by_id")
        .returningResultSet(SINGLE_RESULT, new BookRowMapper());


    retrieveManyJdbcCall = new SimpleJdbcCall(jdbcTemplate).withProcedureName(
            "psp_retrieve_all_genre")
        .returningResultSet(MULTIPLE_RESULT,
            BeanPropertyRowMapper.newInstance(Author.class));

  }

  public Genre create(Genre model) {
    SqlParameterSource in = new MapSqlParameterSource()
        .addValue("genre_name", model.getName());

    Map<String, Object> m = createJdbcCall.execute(in);
    Long id = (Long) m.get("genre_id");
    model.setId(id.intValue());

    return model;
  }

  public Genre retrieveGenreById(Integer id) {
    SqlParameterSource in = new MapSqlParameterSource().addValue("id", id);
    Map<String, Object> m = retrieveOneJdbcCall.execute(in);
    List<Genre> result = (List<Genre>) m.get(SINGLE_RESULT);
    return result.isEmpty() ? null : result.get(0);
  }

  public List<Genre> getAllAuthors() {

    SqlParameterSource recList = new MapSqlParameterSource();
    Map m = retrieveManyJdbcCall.execute(recList);
    return (List<Genre>) m.get(MULTIPLE_RESULT);

  }

}
