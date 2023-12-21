package com.interswitchng.onlinebookstore.dao.util;

import com.interswitchng.onlinebookstore.dto.BookResponse;
import com.interswitchng.onlinebookstore.dto.CartItemResponse;
import com.interswitchng.onlinebookstore.dto.ShoppingCartResponse;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
//
//public class ShoppingCartResponseRowMapper implements RowMapper<ShoppingCartResponse> {
//
//  @Override
//  public ShoppingCartResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
//    ShoppingCartResponse shoppingCartResponse = new ShoppingCartResponse();
//    shoppingCartResponse.setCartId(rs.getInt("cart_id"));
////    shoppingCartResponse.setUserName(rs.getString("user_name"));
////    shoppingCartResponse.setTotalPrice(rs.getBigDecimal("total_price"));
//
//    List<CartItemResponse> items = new ArrayList<>();
//
//    if (rs.next()) {  // Change here: Use if instead of while
//      do {
//
//        CartItemResponse cartItemResponse = new CartItemResponse();
//        cartItemResponse.setCartId(rs.getInt("cart_id"));
//        cartItemResponse.setBookId(rs.getInt("book_id"));
//        cartItemResponse.setQuantityInCart(rs.getInt("quantity"));
//
//        BookResponse bookResponse = new BookResponse();
//        bookResponse.setTitle(rs.getString("book_title"));
//        bookResponse.setIsbn(rs.getString("book_isbn"));
//        bookResponse.setYearOfPublication(rs.getInt("book_year_of_publication"));
//        bookResponse.setAuthorName(rs.getString("author_name"));
//        bookResponse.setAuthorId(rs.getInt("author_id"));
//        bookResponse.setGenreName(rs.getString("genre_name"));
//        bookResponse.setGenreId(rs.getInt("genre_id"));
////      bookResponse.setAvailableCount(rs.getInt("available_book_count"));
//        bookResponse.setPrice(rs.getBigDecimal("price"));
//
//        cartItemResponse.setBookResponse(bookResponse);
//        items.add(cartItemResponse);
//
//      } while (rs.next());
//    }
//
//      shoppingCartResponse.setItems(items);
//
//    return shoppingCartResponse;
//  }
//}


public class ShoppingCartResponseRowMapper implements RowMapper<ShoppingCartResponse> {

  @Override
  public ShoppingCartResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
    ShoppingCartResponse shoppingCartResponse = new ShoppingCartResponse();
    List<CartItemResponse> cartItems = new ArrayList<>();

    shoppingCartResponse.setCartId(rs.getInt("cart_id"));

    while (rs.next()) {
      CartItemResponse cartItemResponse = new CartItemResponse();
      BookResponse bookResponse = new BookResponse();

      cartItemResponse.setId(rs.getInt("item_id"));
      cartItemResponse.setCartId(rs.getInt("cart_id"));
      cartItemResponse.setBookId(rs.getInt("book_id"));


      bookResponse.setTitle(rs.getString("book_title"));
      bookResponse.setPrice(rs.getBigDecimal("price"));
      bookResponse.setIsbn(rs.getString("book_isbn"));
      bookResponse.setYearOfPublication(rs.getInt("book_year_of_publication"));
      bookResponse.setAvailableCount(rs.getInt("available_book_count"));
      bookResponse.setAuthorName(rs.getString("author_name"));
      bookResponse.setAuthorId(rs.getInt("author_id"));
      bookResponse.setGenreName(rs.getString("genre_name"));
      bookResponse.setGenreId(rs.getInt("genre_id"));

      cartItemResponse.setBookResponse(bookResponse);
      cartItems.add(cartItemResponse);
    }

    shoppingCartResponse.setItems(cartItems);

    return shoppingCartResponse;
  }
}
