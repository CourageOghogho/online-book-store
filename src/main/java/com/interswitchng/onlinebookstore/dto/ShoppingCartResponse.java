package com.interswitchng.onlinebookstore.dto;

import com.interswitchng.onlinebookstore.model.CartItem;
import java.util.List;
import lombok.Data;

@Data
public class ShoppingCartResponse {
  private Integer cartId;
  private Integer userId;
  private Data createdAt;

  private List<CartItem> items;

}
