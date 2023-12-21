package com.interswitchng.onlinebookstore.dao.util;

import com.interswitchng.onlinebookstore.model.ShoppingCart;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class CartRowMapper implements RowMapper<ShoppingCart> {

  @Override
  public ShoppingCart mapRow(ResultSet rs, int rowNum) throws SQLException {

    ShoppingCart cart=new ShoppingCart();
    cart.setUserId(rs.getInt("cart_id"));
    cart.setId(rs.getInt("user_id"));
    cart.setCreatedDate(rs.getDate("created_at"));
    cart.setTotalPrice(rs.getBigDecimal("total_price"));
    return cart;
  }
}
