package com.interswitchng.onlinebookstore.service.impl;

import com.interswitchng.onlinebookstore.dao.impl.ShoppingCartDao;
import com.interswitchng.onlinebookstore.dto.BaseResponse;
import com.interswitchng.onlinebookstore.dto.CartItemResponse;
import com.interswitchng.onlinebookstore.dto.ShoppingCartResponse;
import com.interswitchng.onlinebookstore.exceptions.NotFoundException;
import com.interswitchng.onlinebookstore.exceptions.ServiceLayerException;
import com.interswitchng.onlinebookstore.model.CartItem;
import com.interswitchng.onlinebookstore.model.OnlineBookStoreResponseCode;
import com.interswitchng.onlinebookstore.model.ShoppingCart;
import com.interswitchng.onlinebookstore.model.User;
import com.interswitchng.onlinebookstore.service.BookService;
import com.interswitchng.onlinebookstore.service.ShoppingCartService;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {

  private final ShoppingCartDao shoppingCartDao;
  private final BookService bookService;

  @Override
  public void createShoppingCart(Integer userId) {
    ShoppingCart cart=new ShoppingCart();
    cart.setUserId(userId);
    shoppingCartDao.createCart(cart);
  }

  @Transactional
  @Override
  public BaseResponse addItemToCart(Integer userId, int bookId, int quantity) {
    var book= bookService.retrieveByBookId(bookId);
    if (book==null)
      throw new ServiceLayerException("Error adding item to cart, item not in our record");

    if(book.getAvailableCount()<quantity)
      throw new ServiceLayerException("Error adding item to cart, item out of stock");

    ShoppingCart cart=shoppingCartDao.retrieveCartByUserId(userId);
    if(cart==null){
      ShoppingCart model=new ShoppingCart();
      model.setUserId(userId);
      cart=shoppingCartDao.createCart(model);
      if (cart==null) throw new ServiceLayerException("Couldn't create a cart for the user");
    }
    var cartItem= new CartItem();
    cartItem.setBookId(book.getId());
    cartItem.setCartId(cart.getId());
    cartItem.setQuantityInCart(quantity);


    shoppingCartDao.addItem(cartItem);
    return
        new BaseResponse(
            OnlineBookStoreResponseCode.SUCCESS.getCode(), "Added successfully");
  }

  @Override
  public ShoppingCartResponse getCartContents(User user) {

    var cart=shoppingCartDao.retrieveCartByUserId(user.getId());

    var cartItems=shoppingCartDao.retrieveCartItemsByCartId(cart.getId());

    var cartResponse=new ShoppingCartResponse();
    cartResponse.setItems(cartItems);
    cartResponse.setCartId(cart.getId());
    cartResponse.setUserName(user.getUserName());

    return cartResponse;
  }

  @Override
  public BaseResponse updateItemInCart(Integer userId, Integer itemId, Integer quantity) {
    if (quantity<1)throw new IllegalArgumentException("Kindly remove the item instead");

    var entityItem=shoppingCartDao.retrieveItemById(itemId).orElseThrow(
        () ->new NotFoundException(50000,"Item not in our record")
    );

    var book= bookService.retrieveByBookId(entityItem.getBookId());
    if (book==null)
      throw new ServiceLayerException("Error something went wrong");


    if(quantity>book.getAvailableCount()){
      throw new ServiceLayerException("out of stock");
    }
    entityItem.setQuantityInCart(quantity);
    shoppingCartDao.addItem(entityItem);

    return new BaseResponse(OnlineBookStoreResponseCode.SUCCESS.getCode(),"Updated successfully" );
  }
}
