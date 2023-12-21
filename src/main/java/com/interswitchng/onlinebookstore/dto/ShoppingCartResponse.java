package com.interswitchng.onlinebookstore.dto;

import com.interswitchng.onlinebookstore.model.CartItem;
import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

@Data
public class ShoppingCartResponse {
  private Integer cartId;
  private String userName;
  private BigDecimal totalPrice= BigDecimal.ZERO;

  private List<CartItemResponse> items;

}
