package com.interswitchng.onlinebookstore.dao.impl;

import com.interswitchng.onlinebookstore.dao.BaseDao;
import com.interswitchng.onlinebookstore.dao.util.CartItemResponseRowMapper;
import com.interswitchng.onlinebookstore.dao.util.CartItemRowMapper;
import com.interswitchng.onlinebookstore.dao.util.CartRowMapper;
import com.interswitchng.onlinebookstore.dao.util.GenreRowMapper;
import com.interswitchng.onlinebookstore.dao.util.RowCountMapper;
import com.interswitchng.onlinebookstore.dao.util.ShoppingCartResponseRowMapper;
import com.interswitchng.onlinebookstore.dto.CartItemResponse;
import com.interswitchng.onlinebookstore.dto.ShoppingCartResponse;
import com.interswitchng.onlinebookstore.exceptions.NotFoundException;
import com.interswitchng.onlinebookstore.model.CartItem;
import com.interswitchng.onlinebookstore.model.Genre;
import com.interswitchng.onlinebookstore.model.ShoppingCart;
import com.interswitchng.onlinebookstore.utils.OnlineBookStoreResponseCode;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
  private SimpleJdbcCall retrieveItemByIdJdbcCall;
  private SimpleJdbcCall retrieveAllCartItemsByCartIdJdbcCall;

  @Autowired
  @Override
  public void setDataSource(@Qualifier(value = "onlineBookStoreDs") DataSource dataSource) {

    this.jdbcTemplate = new JdbcTemplate(dataSource);

    createJdbcCall = new SimpleJdbcCall(jdbcTemplate)
        .withProcedureName("psp_create_shopping_cart")
        .returningResultSet(SINGLE_RESULT, new CartRowMapper());

    retrieveItemByIdJdbcCall = new SimpleJdbcCall(jdbcTemplate)
        .withProcedureName("psp_retrieve_item_by_item_id")
        .returningResultSet(SINGLE_RESULT, new CartItemRowMapper());

    addItem = new SimpleJdbcCall(jdbcTemplate)
        .withProcedureName("psp_add_item_to_cart")
        .returningResultSet(SINGLE_RESULT, BeanPropertyRowMapper.newInstance(CartItem.class));

    retrieveCartByUserId = new SimpleJdbcCall(jdbcTemplate)
        .withProcedureName("psp_retrieve_cart_by_user_id")
        .returningResultSet(SINGLE_RESULT, new CartRowMapper());

    retrieveCartItemsByUserId = new SimpleJdbcCall(jdbcTemplate)
        .withProcedureName("psp_retrieve_cart_items_by_user_id")
        .returningResultSet(MULTIPLE_RESULT, new ShoppingCartResponseRowMapper())
        .returningResultSet(RESULT_COUNT, new RowCountMapper());

    retrieveAllCartItemsByCartIdJdbcCall = new SimpleJdbcCall(jdbcTemplate).withProcedureName(
            "psp_retrieve_all_cart_items_by_cart_id")
        .returningResultSet(MULTIPLE_RESULT,
            new CartItemResponseRowMapper());

  }



  public ShoppingCart createCart(ShoppingCart cart) {

    SqlParameterSource in = new MapSqlParameterSource()
        .addValue("user_id", cart.getUserId());
    Map<String, Object> m = createJdbcCall.execute(in);
    Long id = (Long) m.get("cart_id");
    cart.setId(id.intValue());

    return cart;
  }

  public Optional<CartItem> retrieveItemById(Integer itemId){

    SqlParameterSource in = new MapSqlParameterSource().addValue("item_id", itemId);
    Map<String, Object> m = retrieveItemByIdJdbcCall.execute(in);
    List<CartItem> result = (List<CartItem>) m.get(SINGLE_RESULT);
    return Optional.of(result.isEmpty() ? null : result.get(0));

  }
  public ShoppingCart retrieveCartByUserId(Integer userId) {
    SqlParameterSource in = new MapSqlParameterSource().addValue("user_id", userId);
    Map<String, Object> m = retrieveCartByUserId.execute(in);
    List<ShoppingCart> result = (List<ShoppingCart>) m.get(SINGLE_RESULT);
    return result.isEmpty() ? null : result.get(0);
  }

  public CartItem addItem(CartItem cartItem) {

    SqlParameterSource in = new MapSqlParameterSource()
        .addValue("cart_id", cartItem.getCartId())
        .addValue("book_id", cartItem.getBookId())
        .addValue("quantity",cartItem.getQuantityInCart());

    Map<String, Object> m = addItem.execute(in);
    Long id = (Long) m.get("item_id");
    cartItem.setId(id.intValue());

    return cartItem;
  }

  public ShoppingCartResponse retrieveCartItemsByUserId(Integer userId) {

    SqlParameterSource in = new MapSqlParameterSource()

        .addValue("userId",userId);

    Map<String, Object> m = retrieveCartItemsByUserId.execute(in);
    List<ShoppingCartResponse> content = (List<ShoppingCartResponse>) m.get(MULTIPLE_RESULT);
    if (content.isEmpty()) {
      throw new NotFoundException(OnlineBookStoreResponseCode.ENTITY_NOT_FOUND.getCode(),
          "Records not found");
    }
    return content.get(0);
  }

  public List<CartItemResponse> retrieveCartItemsByCartId(Integer cartId) {

    SqlParameterSource recList = new MapSqlParameterSource()
        .addValue("cartId",cartId);
    Map m = retrieveAllCartItemsByCartIdJdbcCall.execute(recList);
    return (List<CartItemResponse>) m.get(MULTIPLE_RESULT);
  }

}
