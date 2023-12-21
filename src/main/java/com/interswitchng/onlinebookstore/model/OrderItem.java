package com.interswitchng.onlinebookstore.model;

import com.interswitchng.onlinebookstore.dto.CartItemResponse;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class OrderItem {
  private Integer orderItemId;
  private Integer bookId;
  private String bookTitle;
  private Integer quantity;
  private BigDecimal unitPrice;
  private BigDecimal totalCost;


}
