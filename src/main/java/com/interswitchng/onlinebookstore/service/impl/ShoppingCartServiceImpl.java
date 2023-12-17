package com.interswitchng.onlinebookstore.service.impl;

import com.interswitchng.onlinebookstore.dao.impl.ShoppingCartDao;
import com.interswitchng.onlinebookstore.exceptions.ServiceLayerException;
import com.interswitchng.onlinebookstore.model.CartItem;
import com.interswitchng.onlinebookstore.model.ShoppingCart;
import com.interswitchng.onlinebookstore.service.BookService;
import com.interswitchng.onlinebookstore.service.ShoppingCartService;
import java.util.List;
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
    shoppingCartDao.create(cart);
  }

  @Transactional
  @Override
  public void addItemToCart(String userId, int bookId, int quantity) {
    var book= bookService.retrieveByBookId(bookId);
    if (book==null)
      throw new ServiceLayerException("Error adding item to cart, item not in our record");

    if(book.getAvailableCount()<quantity)
      throw new ServiceLayerException("Error adding item to cart, item out of stock");

    ShoppingCart cart=shoppingCartDao.retrieveCartByUserId(userId);
    var cartItem= new CartItem();
    cartItem.setBookId(bookId);
    cartItem.setCartId(cart.getId());
    cartItem.setQuantity(quantity);

    shoppingCartDao.addItem(cartItem);

  }

  @Override
  public List<CartItem> getCartContents(Integer userId) {

    return shoppingCartDao.retrieveCartItemsByUserId(userId);
  }
}
