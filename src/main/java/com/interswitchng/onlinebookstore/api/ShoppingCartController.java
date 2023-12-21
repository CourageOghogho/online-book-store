package com.interswitchng.onlinebookstore.api;

import com.interswitchng.onlinebookstore.dto.BaseResponse;
import com.interswitchng.onlinebookstore.dto.ShoppingCartResponse;
import com.interswitchng.onlinebookstore.service.ShoppingCartService;
import com.interswitchng.onlinebookstore.utils.SessionUserProvider;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/cart-service/")
public class ShoppingCartController {

  private final ShoppingCartService cartService;
  private final SessionUserProvider userProvider;
  @GetMapping("cart/contents")
  ResponseEntity<ShoppingCartResponse> retrieveCartDetails(){
    var user=userProvider.getSessionUser();
    return ResponseEntity.ok(cartService.getCartContents(user));
  }

  @PutMapping("cart/contents/add")
  ResponseEntity<BaseResponse> addItem(
      @RequestParam(required = true) Integer bookId,
      @RequestParam(required = true) Integer quantity
      ){
    var user=userProvider.getSessionUser();
    return ResponseEntity.ok(cartService.addItemToCart(user.getId(),bookId,quantity));
  }

  @PutMapping("cart/contents/")
  ResponseEntity<BaseResponse> updateCartItemInCart(
      @RequestParam(required = true) Integer bookId,
      @RequestParam(required = true) Integer quantity
  ){
    var user=userProvider.getSessionUser();
    return ResponseEntity.ok(cartService.updateItemInCart(user.getId(),bookId,quantity));
  }
}
