package com.interswitchng.onlinebookstore.model;

import com.interswitchng.onlinebookstore.dto.BookResponse;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class CartItem extends BaseModel{

  private Integer cartId;
  private Integer bookId;
  private Integer quantityInCart;
  private BigDecimal price;
}

