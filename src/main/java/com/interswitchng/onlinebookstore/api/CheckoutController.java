package com.interswitchng.onlinebookstore.api;

import com.interswitchng.onlinebookstore.dto.PaymentResponse;
import com.interswitchng.onlinebookstore.model.Order;
import com.interswitchng.onlinebookstore.model.PaymentMethod;
import com.interswitchng.onlinebookstore.model.User;
import com.interswitchng.onlinebookstore.service.CheckoutService;
import com.interswitchng.onlinebookstore.utils.SessionUserProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/checkout-service/")
public class CheckoutController {

  private final CheckoutService checkoutService;
  private final SessionUserProvider userProvider;


  @PostMapping("process-order/initiate")
  public ResponseEntity<PaymentResponse> checkout(
      @RequestParam Integer paymentMethod) {

    User user=userProvider.getSessionUser();
    PaymentResponse order = checkoutService.processCheckout(user, paymentMethod);
    return ResponseEntity.ok(order);
  }

  @PutMapping("process-order/complete")
  public ResponseEntity<PaymentResponse> completeOrder(
      @RequestParam String  orderReference) {

    return ResponseEntity.ok(checkoutService.completeOrder(orderReference));
  }
}
