package com.interswitchng.onlinebookstore.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import com.interswitchng.onlinebookstore.dto.ShoppingCartResponse;
import com.interswitchng.onlinebookstore.exceptions.NotFoundException;
import com.interswitchng.onlinebookstore.exceptions.ServiceLayerException;
import java.math.BigDecimal;
import java.util.Date;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import com.interswitchng.onlinebookstore.dao.impl.CheckoutDao;
import com.interswitchng.onlinebookstore.dao.impl.ShoppingCartDao;
import com.interswitchng.onlinebookstore.dto.BookResponse;
import com.interswitchng.onlinebookstore.dto.CartItemResponse;
import com.interswitchng.onlinebookstore.dto.PaymentResponse;
import com.interswitchng.onlinebookstore.model.*;
import com.interswitchng.onlinebookstore.service.BookService;
import com.interswitchng.onlinebookstore.service.PaymentService;
import com.interswitchng.onlinebookstore.service.PurchaseHistoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CheckoutServiceImplTest {

  private ShoppingCartDao shoppingCartDao;

  private PurchaseHistoryService purchaseHistoryService;

  private CheckoutDao checkoutDao;
  private BookService bookService;

  private PaymentService paymentService;
  private CheckoutServiceImpl checkoutService;

  @BeforeEach
  void setUp() {
    shoppingCartDao = Mockito.mock(ShoppingCartDao.class);
    purchaseHistoryService = Mockito.mock(PurchaseHistoryService.class);
    checkoutDao = Mockito.mock(CheckoutDao.class);
    bookService = Mockito.mock(BookService.class);
    paymentService = Mockito.mock(PaymentService.class);
    checkoutService = new CheckoutServiceImpl(shoppingCartDao, purchaseHistoryService, checkoutDao, bookService, paymentService);
  }

  @Test
  void processCheckoutHappyPath() {
    // Arrange

    Book testBook=new Book();
    testBook.setCreatedDate(new Date());
    testBook.setTitle("SOMETITLE");
    testBook.setAvailableBookCount(10);
    testBook.setIsbn("123-34-56-2345");
    testBook.setPrice(BigDecimal.valueOf(50));
    testBook.setId(1);
    testBook.setGenreId(1);
    testBook.setAuthorId(1);
    testBook.setYearOfPublication(2023);

    Integer userId = 1;
    Integer paymentMethod = 1;

    ShoppingCartResponse cart = new ShoppingCartResponse();
    CartItemResponse cartItem = new CartItemResponse();
    BookResponse bookInventory = new BookResponse();
    bookInventory.setAvailableCount(10);
    bookInventory.setPrice(BigDecimal.valueOf(50));
    bookInventory.setAuthorName("AUTHOR2");
    bookInventory.setGenreId(1);
    bookInventory.setGenreName("GENRE1");
    bookInventory.setId(1);
    bookInventory.setIsbn("123-34-56-2345");
    bookInventory.setAuthorId(1);
    bookInventory.setAvailableCount(20);
    bookInventory.setYearOfPublication(2023);
    cartItem.setBookResponse(bookInventory);
    cartItem.setQuantityInCart(3);
    cart.setTotalPrice(BigDecimal.valueOf(200));
    cart.setItems(Collections.singletonList(cartItem));

    Order orderResponse=new Order();
    orderResponse.setOrderStatus(OrderStatus.INITIATED);
    orderResponse.setUserId(1);
    orderResponse.setTotalAmount(BigDecimal.valueOf(200));
    orderResponse.setReference(UUID.randomUUID().toString());
    orderResponse.setId(1);



    Order updatedOrderResponse=new Order();
    updatedOrderResponse.setOrderStatus(OrderStatus.SUCCESS);
    updatedOrderResponse.setUserId(1);
    updatedOrderResponse.setTotalAmount(BigDecimal.valueOf(200));
    updatedOrderResponse.setReference(UUID.randomUUID().toString());
    updatedOrderResponse.setId(1);


    OrderItem orderItem=new OrderItem();
    orderItem.setOrderItemId(1);
    orderItem.setBookTitle("SOME TITLE");
    orderItem.setQuantity(2);
    orderItem.setTotalCost(BigDecimal.valueOf(100));
    orderItem.setBookId(1);
    orderItem.setUnitPrice(BigDecimal.valueOf(50));
    updatedOrderResponse.setOrderItems(Collections.singletonList(orderItem));

    PaymentResponse paymentResponse=new PaymentResponse();
    paymentResponse.setOrder(updatedOrderResponse);
    paymentResponse.setStatusCode(PaymentStatus.SUCCESS.getCode());


    when(shoppingCartDao.retrieveCartItemsByUserId(userId)).thenReturn(cart);
    when(bookService.updateBookInventory(Mockito.any(Book.class))).thenReturn(testBook);
    when(checkoutDao.saveOrder(Mockito.any(Order.class))).thenReturn(new Order());
    when(paymentService.initiatePayment(Mockito.any(Order.class), eq(paymentMethod))).thenReturn(paymentResponse);
    when(checkoutDao.updateOrder(Mockito.any(Order.class))).thenReturn(updatedOrderResponse);

    PaymentResponse result = checkoutService.processCheckout(userId, paymentMethod);

    assertNotNull(result);
    assertNotNull(result.getOrder());
    assertEquals(OrderStatus.INITIATED, result.getOrder().getOrderStatus());
  }

  @Test
  void processCheckoutSadPathCartNotFound() {
    // Arrange
    Integer userId = 1;
    Integer paymentMethod = 1;

    when(shoppingCartDao.retrieveCartItemsByUserId(userId)).thenReturn(null);

    // Act & Assert
    assertThrows(
        NotFoundException.class, () -> checkoutService.processCheckout(userId, paymentMethod));
  }

  @Test
  void processCheckoutSadPathBookUpdateFailed() {
    // Arrange
    Integer userId = 1;
    Integer paymentMethod = 1;

    ShoppingCartResponse cart = new ShoppingCartResponse();
    CartItemResponse cartItem = new CartItemResponse();
    BookResponse bookInventory = new BookResponse();
    bookInventory.setAvailableCount(10);
    cartItem.setBookResponse(bookInventory);
    cartItem.setQuantityInCart(3);
    cart.setItems(Collections.singletonList(cartItem));

    when(shoppingCartDao.retrieveCartItemsByUserId(userId)).thenReturn(cart);
    when(bookService.updateBookInventory(Mockito.any(Book.class))).thenReturn(null);

    // Act & Assert
    assertThrows(
        ServiceLayerException.class, () -> checkoutService.processCheckout(userId, paymentMethod));
  }

  @Test
  void completeOrderHappyPath() {
    // Arrange
    String orderReference = UUID.randomUUID().toString();
    Order order = new Order();
    order.setReference(orderReference);

    when(checkoutDao.retrieveOrderByReference(orderReference)).thenReturn(java.util.Optional.of(order));
    when(paymentService.completePayment(Mockito.any(Order.class))).thenReturn(new PaymentResponse());

    // Act
    PaymentResponse result = checkoutService.completeOrder(orderReference);

    // Assert
    assertNotNull(result);
    assertNotNull(result.getOrder());
  }

  @Test
  void completeOrderSadPathOrderNotFound() {
    // Arrange
    String orderReference = UUID.randomUUID().toString();

    when(checkoutDao.retrieveOrderByReference(orderReference)).thenReturn(java.util.Optional.empty());

    // Act & Assert
    assertThrows(ServiceLayerException.class, () -> checkoutService.completeOrder(orderReference));
  }
}
