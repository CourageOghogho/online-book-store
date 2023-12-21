package com.interswitchng.onlinebookstore.dao.util;


import com.interswitchng.onlinebookstore.model.OrderHistory;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderHistoryRowMapper implements RowMapper<OrderHistory> {

  @Override
  public OrderHistory mapRow(ResultSet rs, int rowNum) throws SQLException {
    OrderHistory orderHistory = new OrderHistory();
    orderHistory.setId(rs.getInt("order_history_id"));
    orderHistory.setOrderId(rs.getInt("order_id"));
    orderHistory.setStatus(rs.getString("status"));
    orderHistory.setUpdateDate(rs.getDate("update_date"));
    orderHistory.setOrderReferenceId(rs.getString("order_reference_id"));
    orderHistory.setBookId(rs.getInt("book_id"));
    orderHistory.setTitle(rs.getString("title"));
    orderHistory.setAuthorName(rs.getString("author_name"));
    orderHistory.setUserId(rs.getInt("user_id"));
    orderHistory.setTotalAmount(rs.getBigDecimal("total_amount"));
    orderHistory.setQuantityOrdered(rs.getInt("quantity_ordered"));

    return orderHistory;
  }
}


