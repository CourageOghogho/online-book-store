package com.interswitchng.onlinebookstore.model;

import lombok.Data;

@Data
public class CartItem extends BaseModel{

  private Integer cartId;
  private Integer bookId;
  private Integer quantity;
  private Book book;
}

