package com.interswitchng.onlinebookstore.dao.util;

import com.interswitchng.onlinebookstore.model.Book;
import com.interswitchng.onlinebookstore.model.CartItem;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class CartItemRowMapper  implements RowMapper<CartItem> {

  @Override
  public CartItem mapRow(ResultSet rs, int rowNum) throws SQLException {

    CartItem cartItem =new CartItem();

    cartItem.setId(rs.getInt("item_id"));
    cartItem.setCartId(rs.getInt("cart_id"));
    cartItem.setBookId(rs.getInt("book_id"));
    cartItem.setQuantity(rs.getInt("quantity"));
    cartItem.setCreatedDate(rs.getDate("created_at"));


    Book book = new Book();
    book.setTitle(rs.getString("book_title"));
    book.setIsbn(rs.getString("book_isbn"));
    book.setYearOfPublication(rs.getInt("book_year_of_publication"));

    cartItem.setBook(book);
    return cartItem;
  }
}
