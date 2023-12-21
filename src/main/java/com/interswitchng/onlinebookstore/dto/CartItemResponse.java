package com.interswitchng.onlinebookstore.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class CartItemResponse {
  private Integer cartId;
  private Integer bookId;
  private Integer quantityInCart;
  private BigDecimal subTotalPrice;
  private BookResponse bookResponse;

}
