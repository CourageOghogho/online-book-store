package com.interswitchng.onlinebookstore.model;

import java.math.BigDecimal;
import javax.xml.crypto.Data;
@lombok.Data
public class ShoppingCart extends BaseModel{
  private Integer userId;
  private BigDecimal totalPrice;

}




