package com.interswitchng.onlinebookstore.dao;

import com.interswitchng.onlinebookstore.dao.util.Jsr310SupportedBeanPropertySqlParameterSource;
import com.interswitchng.onlinebookstore.model.BaseModel;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;


@SuppressWarnings(value = "unchecked")
public abstract class BaseDao<T extends BaseModel> {

  protected static final String SINGLE_RESULT = "single";
  protected static final String MULTIPLE_RESULT = "list";
  protected static final String RESULT_COUNT = "count";

  protected JdbcTemplate jdbcTemplate;

  protected SimpleJdbcCall createJdbcCall;
  protected SimpleJdbcCall retrieveOneJdbcCall;
  protected SimpleJdbcCall retrieveManyJdbcCall;
  protected SimpleJdbcCall updateJdbcCall;
  protected SimpleJdbcCall deleteJdbcCall;


  public abstract void setDataSource(DataSource dataSource);

  public T create(T model) {
    Jsr310SupportedBeanPropertySqlParameterSource in = new Jsr310SupportedBeanPropertySqlParameterSource(
        model);
    Map<String, Object> m = createJdbcCall.execute(in);
    Integer id = (Integer) m.get("id");
    model.setId(id);

    return model;
  }

  public T retrieve(Integer id) {
    SqlParameterSource in = new MapSqlParameterSource("id", id);
    Map<String, Object> m = retrieveOneJdbcCall.execute(in);
    List<T> result = (List<T>) m.get(SINGLE_RESULT);
    return result.isEmpty() ? null : result.get(0);
  }
}