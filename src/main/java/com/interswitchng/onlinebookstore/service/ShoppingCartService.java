package com.interswitchng.onlinebookstore.service;

import com.interswitchng.onlinebookstore.model.CartItem;
import java.util.List;

public interface ShoppingCartService {

  void createShoppingCart(Integer userId);
  void addItemToCart(String userId, int bookId, int quantity);
  List<CartItem> getCartContents(Integer userId);
}
