package com.interswitchng.onlinebookstore.service.impl;

import com.interswitchng.onlinebookstore.dao.impl.CheckoutDao;
import com.interswitchng.onlinebookstore.dao.impl.ShoppingCartDao;
import com.interswitchng.onlinebookstore.dto.BookResponse;
import com.interswitchng.onlinebookstore.dto.CartItemResponse;
import com.interswitchng.onlinebookstore.dto.PaymentResponse;
import com.interswitchng.onlinebookstore.exceptions.NotFoundException;
import com.interswitchng.onlinebookstore.exceptions.ServiceLayerException;
import com.interswitchng.onlinebookstore.model.Order;
import com.interswitchng.onlinebookstore.model.OrderStatus;
import com.interswitchng.onlinebookstore.model.PaymentMethod;
import com.interswitchng.onlinebookstore.model.PaymentStatus;
import com.interswitchng.onlinebookstore.model.User;
import com.interswitchng.onlinebookstore.service.BookService;
import com.interswitchng.onlinebookstore.service.CheckoutService;
import com.interswitchng.onlinebookstore.service.PaymentService;
import com.interswitchng.onlinebookstore.service.PurchaseHistoryService;
import com.interswitchng.onlinebookstore.service.ShoppingCartService;
import com.interswitchng.onlinebookstore.utils.CustomModelMapper;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CheckoutServiceImpl implements CheckoutService {
  private final ShoppingCartService shoppingCartService;
  private final PurchaseHistoryService purchaseHistoryService;
  private final CheckoutDao checkoutDao;
  private final BookService bookService;
  private final PaymentService paymentService;

  private final Logger logger = LoggerFactory.getLogger(BookServiceImpl.class);



  @Override
  @Transactional
  public PaymentResponse processCheckout(User user, Integer paymentMethod) {
    var optionalPaymentMethod= PaymentMethod.get(paymentMethod).orElseThrow(
        ()->new NotFoundException(50000,"Invalid Payment method")
    );

    var cart=shoppingCartService.getCartContents(user);
    if (cart==null) throw new NotFoundException(50000,"No cart found for the user");

    for (CartItemResponse cartItem : cart.getItems()) {

      int itemQuantity = cartItem.getQuantityInCart();

      BookResponse bookInventory =cartItem.getBookResponse();

      int updatedQuantity = bookInventory.getAvailableCount() - itemQuantity;
      bookInventory.setAvailableCount(updatedQuantity);

      bookService.updateBookInventory(CustomModelMapper.BookOfBookResponse(bookInventory));
    }

    var order=new Order();
    order.setOrderStatus(OrderStatus.INITIATED);
    order.setUserId(user.getId());
    order.setTotalAmount(cart.getTotalPrice());
    order.setReference(UUID.randomUUID().toString());

    var cartContent=shoppingCartService.getCartContents(user);

    var orderItems= cartContent.getItems().parallelStream()
            .map(CustomModelMapper::orderItemOfCartItem).toList();

    order.setOrderItems(orderItems);
    logger.info("order initiated for: "+order.toString());

    OrderStatus orderStatus=OrderStatus.INITIATED;
    order.setOrderStatus(orderStatus);
    var savedOrder=checkoutDao.saveOrder(order);
    if(savedOrder==null) throw new ServiceLayerException("Could not process order");

    var paymentResponse=paymentService.initiatePayment(savedOrder,optionalPaymentMethod.getCode());

    logger.info("order payment initiated with status description: "+paymentResponse.getDescription());
    checkoutDao.updateOrder(savedOrder);
    paymentResponse.setOrder(savedOrder);
    return paymentResponse;
  }

  @Override
  public PaymentResponse completeOrder(String  orderReference) {
    var order=checkoutDao.retrieveOrderByReference(orderReference).orElseThrow(
        ()-> new ServiceLayerException("Invalid Reference code:  record not found")
    );
    PaymentResponse response=paymentService.completePayment( order);
//
//    CompletableFuture.runAsync(() -> purchaseHistoryService.save(order));
    return response;
  }


}
