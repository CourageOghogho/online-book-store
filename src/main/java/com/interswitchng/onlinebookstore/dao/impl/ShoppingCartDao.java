package com.interswitchng.onlinebookstore.dao.impl;

import com.interswitchng.onlinebookstore.dao.BaseDao;
import com.interswitchng.onlinebookstore.dao.util.CartItemRowMapper;
import com.interswitchng.onlinebookstore.dao.util.CartRowMapper;
import com.interswitchng.onlinebookstore.dao.util.RowCountMapper;
import com.interswitchng.onlinebookstore.dto.BookResponse;
import com.interswitchng.onlinebookstore.exceptions.NotFoundException;
import com.interswitchng.onlinebookstore.model.CartItem;
import com.interswitchng.onlinebookstore.model.ShoppingCart;
import com.interswitchng.onlinebookstore.utils.OnlineBookStoreResponseCode;
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
public class ShoppingCartDao extends BaseDao<ShoppingCart> {

  private SimpleJdbcCall retrieveCartByUserId;
  private SimpleJdbcCall retrieveCartItemsByUserId;
  private SimpleJdbcCall addItem;

  @Autowired
  @Override
  public void setDataSource(@Qualifier(value = "onlineBookStoreDs") DataSource dataSource) {

    this.jdbcTemplate = new JdbcTemplate(dataSource);

    createJdbcCall = new SimpleJdbcCall(jdbcTemplate)
        .withProcedureName("psp_create_shopping_cart")
        .returningResultSet(SINGLE_RESULT, BeanPropertyRowMapper.newInstance(ShoppingCart.class));

    addItem = new SimpleJdbcCall(jdbcTemplate)
        .withProcedureName("psp_add_item_to_cart")
        .returningResultSet(SINGLE_RESULT, BeanPropertyRowMapper.newInstance(CartItem.class));

    retrieveCartByUserId = new SimpleJdbcCall(jdbcTemplate)
        .withProcedureName("psp_retrieve_cart_by_user_id")
        .returningResultSet(SINGLE_RESULT, new CartRowMapper());

    retrieveCartItemsByUserId = new SimpleJdbcCall(jdbcTemplate)
        .withProcedureName("psp_retrieve_cart_items_by_user_id")
        .returningResultSet(MULTIPLE_RESULT, new CartItemRowMapper())
        .returningResultSet(RESULT_COUNT, new RowCountMapper());


  }

  public ShoppingCart retrieveCartByUserId(String userId) {
    SqlParameterSource in = new MapSqlParameterSource().addValue("user_id", userId);
    Map<String, Object> m = retrieveCartByUserId.execute(in);
    List<ShoppingCart> result = (List<ShoppingCart>) m.get(SINGLE_RESULT);
    return result.isEmpty() ? null : result.get(0);
  }

  public CartItem addItem(CartItem cartItem) {

    SqlParameterSource in = new MapSqlParameterSource()
        .addValue("book_id", cartItem.getBookId())
        .addValue("quantity",cartItem.getQuantity());

    Map<String, Object> m = addItem.execute(in);
    Long id = (Long) m.get("book_id");
    cartItem.setId(id.intValue());

    return cartItem;
  }

  public List<CartItem> retrieveCartItemsByUserId(Integer userId) {

    SqlParameterSource in = new MapSqlParameterSource()

        .addValue("user_id",userId);

    Map<String, Object> m = retrieveCartItemsByUserId.execute(in);
    List<CartItem> content = (List<CartItem>) m.get(MULTIPLE_RESULT);
    if (content.isEmpty()) {
      throw new NotFoundException(OnlineBookStoreResponseCode.ENTITY_NOT_FOUND.getCode(),
          "Records not found");
    }
    return content;
  }
}
