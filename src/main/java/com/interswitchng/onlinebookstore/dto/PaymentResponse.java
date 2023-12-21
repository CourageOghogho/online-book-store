package com.interswitchng.onlinebookstore.dto;

import com.interswitchng.onlinebookstore.model.Order;
import com.interswitchng.onlinebookstore.service.PaymentService;
import lombok.Data;

@Data
public class PaymentResponse {
  private Integer statusCode;
  private String description;
  private String paymentInstruction;
  private Order order;

}
