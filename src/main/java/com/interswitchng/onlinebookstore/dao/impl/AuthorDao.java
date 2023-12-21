package com.interswitchng.onlinebookstore.dao.impl;

import com.interswitchng.onlinebookstore.dao.BaseDao;
import com.interswitchng.onlinebookstore.dao.util.AuthorRowMapper;
import com.interswitchng.onlinebookstore.dao.util.BookRowMapper;
import com.interswitchng.onlinebookstore.model.Author;
import com.interswitchng.onlinebookstore.model.Book;
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
public class AuthorDao extends BaseDao  {

  private static final String AUTHOR = "author_name";


  @Autowired
  @Override
  public void setDataSource(@Qualifier(value = "onlineBookStoreDs") DataSource dataSource) {
    this.jdbcTemplate = new JdbcTemplate(dataSource);

    createJdbcCall = new SimpleJdbcCall(jdbcTemplate)
        .withProcedureName("psp_create_author")
        .returningResultSet(SINGLE_RESULT, new AuthorRowMapper());

    retrieveOneJdbcCall = new SimpleJdbcCall(jdbcTemplate)
        .withProcedureName("psp_retrieve_author_by_id")
        .returningResultSet(SINGLE_RESULT, new AuthorRowMapper());


    retrieveManyJdbcCall = new SimpleJdbcCall(jdbcTemplate).withProcedureName(
            "psp_retrieve_all_author")
        .returningResultSet(MULTIPLE_RESULT,
           new AuthorRowMapper());

  }

  public Author create(Author model) {
    SqlParameterSource in = new MapSqlParameterSource()
        .addValue(AUTHOR, model.getName());

    Map<String, Object> m = createJdbcCall.execute(in);
    List<Author> result = (List<Author>) m.get(SINGLE_RESULT);
    return result.isEmpty() ? null : result.get(0);
  }

  public Author retrieveAuthorById(Integer id) {
    SqlParameterSource in = new MapSqlParameterSource().addValue("id", id);
    Map<String, Object> m = retrieveOneJdbcCall.execute(in);
    List<Author> result = (List<Author>) m.get(SINGLE_RESULT);
    return result.isEmpty() ? null : result.get(0);
  }

  public List<Author> getAllAuthors() {

    SqlParameterSource recList = new MapSqlParameterSource();
    Map m = retrieveManyJdbcCall.execute(recList);
    return (List<Author>) m.get(MULTIPLE_RESULT);

  }


}
