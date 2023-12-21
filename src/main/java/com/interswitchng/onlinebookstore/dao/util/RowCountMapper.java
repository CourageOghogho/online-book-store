package com.interswitchng.onlinebookstore.dao.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class RowCountMapper implements RowMapper<Long> {
  @Override
  public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
    try {
      return rs.getLong(1);
    } catch (IndexOutOfBoundsException exc) {
      return 0L;
    }
  }
}
