package com.interswitchng.onlinebookstore.service;

import com.interswitchng.onlinebookstore.dto.BaseResponse;
import com.interswitchng.onlinebookstore.dto.ShoppingCartResponse;
import com.interswitchng.onlinebookstore.model.CartItem;
import java.util.List;

public interface ShoppingCartService {

  void createShoppingCart(Integer userId);
  BaseResponse addItemToCart(Integer userId, int bookId, int quantity);
  ShoppingCartResponse getCartContents(Integer userId);

  BaseResponse updateItemInCart(Integer id, Integer bookId, Integer quantity);
}
