package com.interswitchng.onlinebookstore.dao.util;

import com.interswitchng.onlinebookstore.dto.BookResponse;
import com.interswitchng.onlinebookstore.dto.CartItemResponse;
import com.interswitchng.onlinebookstore.model.Order;
import com.interswitchng.onlinebookstore.model.OrderItem;
import com.interswitchng.onlinebookstore.model.OrderStatus;
import com.interswitchng.onlinebookstore.model.PaymentMethod;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.jdbc.core.RowMapper;



public class OrderRowMapper implements RowMapper<Order> {
  @Override
  public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
    Order order = new Order();
    order.setId(rs.getInt("order_id"));
    order.setUserId(rs.getInt("user_id"));
    order.setReference(rs.getString("reference_id"));
    order.setOrderStatus(OrderStatus.get(rs.getInt("order_status")).get());
    order.setTotalAmount(rs.getBigDecimal("total_amount"));
    order.setCreatedDate(rs.getDate("date_created")!= null ? new Date(
        rs.getTimestamp("date_created").getTime()) : null);
    return order;
  }
}