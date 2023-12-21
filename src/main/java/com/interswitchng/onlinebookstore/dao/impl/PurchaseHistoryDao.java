package com.interswitchng.onlinebookstore.dao.impl;

import com.interswitchng.onlinebookstore.dao.BaseDao;
import com.interswitchng.onlinebookstore.dao.util.OrderHistoryRowMapper;
import com.interswitchng.onlinebookstore.dao.util.RowCountMapper;
import com.interswitchng.onlinebookstore.dto.PageParams;
import com.interswitchng.onlinebookstore.exceptions.NotFoundException;
import com.interswitchng.onlinebookstore.model.OrderHistory;
import com.interswitchng.onlinebookstore.model.Page;
import com.interswitchng.onlinebookstore.utils.OnlineBookStoreResponseCode;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

@Repository
public class PurchaseHistoryDao extends BaseDao<OrderHistory> {


  private SimpleJdbcCall historyForUser;
  private static final String PAGE_NUM = "page_num";
  private static final String PAGE_SIZE = "page_size";


  @Autowired
  @Override
  public void setDataSource(@Qualifier(value = "onlineBookStoreDs") DataSource dataSource) {
    this.jdbcTemplate = new JdbcTemplate(dataSource);


    historyForUser = new SimpleJdbcCall(jdbcTemplate)
        .withProcedureName("psp_retrieve_order_history_by_user_id")
        .returningResultSet(MULTIPLE_RESULT, new OrderHistoryRowMapper())
        .returningResultSet(RESULT_COUNT, new RowCountMapper());

  }

  public  Page<OrderHistory>  historyForUser(PageParams params,Integer userId) {
    SqlParameterSource in = new MapSqlParameterSource()
        .addValue("user_id",userId )
        .addValue(PAGE_NUM, params.getPageNum())
        .addValue(PAGE_SIZE, params.getPageSize());
    Map<String, Object> m = historyForUser.execute(in);
    List<OrderHistory> content = (List<OrderHistory>) m.get(MULTIPLE_RESULT);
    if (content.isEmpty()) {
      throw new NotFoundException(OnlineBookStoreResponseCode.ENTITY_NOT_FOUND.getCode(),
          "Records not found");
    }
    Long count = (Long) ((List) m.get(RESULT_COUNT)).get(0);
    return new Page(count, content);

  }
}
