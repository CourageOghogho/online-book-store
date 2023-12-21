package com.interswitchng.onlinebookstore.dao.util;

import com.interswitchng.onlinebookstore.dto.BookResponse;
import com.interswitchng.onlinebookstore.dto.CartItemResponse;
import com.interswitchng.onlinebookstore.model.Order;
import com.interswitchng.onlinebookstore.model.OrderItem;
import com.interswitchng.onlinebookstore.model.OrderStatus;
import com.interswitchng.onlinebookstore.model.PaymentMethod;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.jdbc.core.RowMapper;

public class OrderRowMapper implements RowMapper<Order> {

  @Override
  public Order mapRow(ResultSet rs, int rowNum) throws SQLException {

    var order=new Order();
    order.setId(rs.getInt("order_id"));
    order.setUserId(rs.getInt("user_id"));
    order.setReference(rs.getString("reference_id"));
    PaymentMethod paymentMethod=PaymentMethod.get(rs.getInt("payment_method")).get();
    OrderStatus status=OrderStatus.get(rs.getInt("order_status")).get();
    order.setPaymentMethod(paymentMethod);
    order.setOrderStatus(status);

    List<OrderItem> items = new ArrayList<>();
    BigDecimal totalAmount = BigDecimal.ZERO;

    do {

      OrderItem item=new OrderItem();
      item.setQuantity(rs.getInt("quantity"));
      item.setUnitPrice(rs.getBigDecimal("price"));
      item.setBookTitle(rs.getString("book_title"));
      item.setOrderItemId(rs.getInt("order_item_id"));

      item.setTotalCost(item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())));

      items.add(item);

      totalAmount = totalAmount.add(item.getTotalCost());

    } while (rs.next());

    order.setTotalAmount(totalAmount);
    order.setOrderItems(items);

    return order;
  }
}
