package com.interswitchng.onlinebookstore.dao.impl;

import com.interswitchng.onlinebookstore.dao.BaseDao;
import com.interswitchng.onlinebookstore.dao.util.BookRowMapper;
import com.interswitchng.onlinebookstore.dao.util.OrderRowMapper;
import com.interswitchng.onlinebookstore.dao.util.RowCountMapper;
import com.interswitchng.onlinebookstore.dto.BookResponse;
import com.interswitchng.onlinebookstore.exceptions.NotFoundException;
import com.interswitchng.onlinebookstore.model.Book;
import com.interswitchng.onlinebookstore.model.Order;
import com.interswitchng.onlinebookstore.model.OrderStatus;
import com.interswitchng.onlinebookstore.model.PaymentMethod;
import com.interswitchng.onlinebookstore.utils.OnlineBookStoreResponseCode;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

@Repository
public class CheckoutDao extends BaseDao<Order> {

  private SimpleJdbcCall saveOrder;
  private SimpleJdbcCall retrieveOrderByReference;

  private SimpleJdbcCall updateOrder;
  private SimpleJdbcCall saveOrderHistory;
  private SimpleJdbcCall saveOrderItems;

  private SimpleJdbcCall clearCartByUserId;


  @Autowired
  @Override
  public void setDataSource(@Qualifier(value = "onlineBookStoreDs") DataSource dataSource) {
    this.jdbcTemplate = new JdbcTemplate(dataSource);

    saveOrder = new SimpleJdbcCall(jdbcTemplate)
        .withProcedureName("psp_create_order")
        .returningResultSet(SINGLE_RESULT, new OrderRowMapper());

    saveOrderItems = new SimpleJdbcCall(jdbcTemplate)
        .withProcedureName("sp_save_order_items")
        .returningResultSet(MULTIPLE_RESULT, new OrderRowMapper());

    saveOrderHistory = new SimpleJdbcCall(jdbcTemplate)
        .withProcedureName("sp_create_order_history")
        .returningResultSet(SINGLE_RESULT, new OrderRowMapper());

    updateOrder = new SimpleJdbcCall(jdbcTemplate)
        .withProcedureName("psp_update_order")
        .returningResultSet(SINGLE_RESULT, new OrderRowMapper());

    retrieveOrderByReference = new SimpleJdbcCall(jdbcTemplate)
        .withProcedureName("psp_retrieve_by_reference")
        .returningResultSet(SINGLE_RESULT, new OrderRowMapper());


    retrieveOneJdbcCall = new SimpleJdbcCall(jdbcTemplate)
        .withProcedureName("psp_retrieve_order_by_id")
        .returningResultSet(SINGLE_RESULT, new OrderRowMapper());

  }

  public Order saveOrder(Order order) {

    SqlParameterSource in = new MapSqlParameterSource()
        .addValue("user_id", order.getUserId())
        .addValue("reference_id", order.getReference())
        .addValue("order_status", order.getOrderStatus().getCode())
        .addValue("total_amount", order.getTotalAmount());

    Map<String, Object> m = saveOrder.execute(in);
    List<Order> result = (List<Order>) m.get(SINGLE_RESULT);
    return result.isEmpty() ? null : result.get(0);
  }

  public Optional<Order> retrieveOrderByReference(String orderReference) {

    SqlParameterSource in = new MapSqlParameterSource()
        .addValue("reference_id",orderReference);
    Map<String, Object> m = retrieveOrderByReference.execute(in);
    List<Order> content = (List<Order>) m.get(MULTIPLE_RESULT);
    if (content.isEmpty()) {
      throw new NotFoundException(OnlineBookStoreResponseCode.ENTITY_NOT_FOUND.getCode(),
          "Records not found");
    }

    return Optional.of(content.get(0));
  }

  public Order updateOrder(Order order) {
    SqlParameterSource in = new MapSqlParameterSource()
        .addValue("reference_id", order.getReference())
        .addValue("order_status", order.getOrderStatus().getCode());

    Map<String, Object> m = updateOrder.execute(in);
    List<Order> result = (List<Order>) m.get(SINGLE_RESULT);
    return result.isEmpty() ? null : result.get(0);

  }

}
