package com.interswitchng.onlinebookstore.dao.util;

import com.interswitchng.onlinebookstore.dto.CartItemResponse;
import com.interswitchng.onlinebookstore.model.Author;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;



import com.interswitchng.onlinebookstore.dto.BookResponse;
import com.interswitchng.onlinebookstore.dto.CartItemResponse;
import com.interswitchng.onlinebookstore.dto.ShoppingCartResponse;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CartItemResponseRowMapper implements RowMapper<CartItemResponse> {

  @Override
  public CartItemResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
    CartItemResponse cartItemResponse = new CartItemResponse();
    cartItemResponse.setId(rs.getInt("item_id"));
    cartItemResponse.setCartId(rs.getInt("cart_id"));
    cartItemResponse.setBookId(rs.getInt("book_id"));
    cartItemResponse.setQuantityInCart(rs.getInt("quantity"));

    BookResponse bookResponse = new BookResponse();
    bookResponse.setId(rs.getInt("book_id"));
    bookResponse.setTitle(rs.getString("book_title"));
    bookResponse.setIsbn(rs.getString("book_isbn"));
    bookResponse.setYearOfPublication(rs.getInt("book_year_of_publication"));
    bookResponse.setAuthorName(rs.getString("author_name"));
    bookResponse.setAuthorId(rs.getInt("author_id"));
    bookResponse.setGenreName(rs.getString("genre_name"));
    bookResponse.setGenreId(rs.getInt("genre_id"));
    bookResponse.setAvailableCount(rs.getInt("available_book_count"));
    bookResponse.setPrice(rs.getBigDecimal("price"));

    cartItemResponse.setSubTotalPrice(bookResponse.getPrice().multiply(
        BigDecimal.valueOf(cartItemResponse.getQuantityInCart())));

    cartItemResponse.setBookResponse(bookResponse);

    return cartItemResponse;
  }
}
