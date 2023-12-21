package com.interswitchng.onlinebookstore.model;

import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

@Data
public class Order extends BaseModel{
  private int userId;
  private List<OrderItem> orderItems;
  private PaymentMethod paymentMethod;
  private BigDecimal totalAmount;
  private OrderStatus orderStatus;
  private String reference;

}
