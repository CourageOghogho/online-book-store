package com.interswitchng.onlinebookstore.utils;

import com.interswitchng.onlinebookstore.dto.BookResponse;
import com.interswitchng.onlinebookstore.dto.CartItemResponse;
import com.interswitchng.onlinebookstore.model.Book;
import com.interswitchng.onlinebookstore.model.OrderItem;
import java.math.BigDecimal;

public class CustomModelMapper {
  public static OrderItem orderItemOfCartItem(CartItemResponse cartItem){
    var order=new OrderItem();
    order.setQuantity(cartItem.getQuantityInCart());
    order.setBookTitle(cartItem.getBookResponse().getTitle());
    order.setUnitPrice(cartItem.getBookResponse().getPrice());
    order.setBookId(cartItem.getBookId());
    order.setTotalCost(order.getUnitPrice().multiply(BigDecimal.valueOf(order.getQuantity())));
    return order;
  }

  public static Book BookOfBookResponse(BookResponse response){
    Book book =new Book();
    book.setId(response.getId());
    book.setIsbn(response.getIsbn());
    book.setTitle(response.getTitle());
    book.setGenreId(response.getGenreId());
    book.setAuthorId(response.getAuthorId());
    book.setYearOfPublication(response.getYearOfPublication());
    book.setAvailableBookCount(response.getAvailableCount());
    book.setPrice(response.getPrice());
    book.setCreatedDate(response.getCreatedDate());
    return book;
  }
}
