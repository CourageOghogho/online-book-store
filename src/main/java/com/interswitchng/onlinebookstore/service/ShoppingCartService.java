package com.interswitchng.onlinebookstore.service;

import com.interswitchng.onlinebookstore.dto.BaseResponse;
import com.interswitchng.onlinebookstore.dto.ShoppingCartResponse;
import com.interswitchng.onlinebookstore.model.CartItem;
import com.interswitchng.onlinebookstore.model.User;
import java.util.List;

public interface ShoppingCartService {

  void createShoppingCart(Integer userId);
  BaseResponse addItemToCart(Integer userId, int bookId, int quantity);
  ShoppingCartResponse getCartContents( User user);

  BaseResponse updateItemInCart(Integer id, Integer bookId, Integer quantity);
}
