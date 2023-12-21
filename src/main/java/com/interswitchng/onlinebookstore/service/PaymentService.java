package com.interswitchng.onlinebookstore.service;

import com.interswitchng.onlinebookstore.dto.PaymentResponse;
import com.interswitchng.onlinebookstore.model.Order;
import com.interswitchng.onlinebookstore.model.PaymentMethod;

public interface PaymentService {
  PaymentResponse initiatePayment(Order order, Integer method);

  PaymentResponse completePayment(Order order);
}
