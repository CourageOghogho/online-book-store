package com.interswitchng.onlinebookstore.dto;

import com.interswitchng.onlinebookstore.model.BaseModel;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class CartItemResponse extends BaseModel {
  private Integer cartId;
  private Integer bookId;
  private Integer quantityInCart;
  private BigDecimal subTotalPrice;
  private BookResponse bookResponse;

}
