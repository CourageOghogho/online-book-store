package com.interswitchng.onlinebookstore.service;

import com.interswitchng.onlinebookstore.dto.PaymentResponse;
import com.interswitchng.onlinebookstore.model.Order;

public interface CheckoutService {

  PaymentResponse processCheckout(Integer userId, Integer paymentMethod);

  PaymentResponse completeOrder(String  orderReference);
}
