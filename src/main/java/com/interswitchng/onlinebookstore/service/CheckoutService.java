package com.interswitchng.onlinebookstore.service;

import com.interswitchng.onlinebookstore.dto.PaymentResponse;
import com.interswitchng.onlinebookstore.model.Order;
import com.interswitchng.onlinebookstore.model.User;

public interface CheckoutService {

  PaymentResponse processCheckout(User user, Integer paymentMethod);

  PaymentResponse completeOrder(String  orderReference);
}
