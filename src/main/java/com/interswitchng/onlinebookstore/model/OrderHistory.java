package com.interswitchng.onlinebookstore.model;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

@Data
public class OrderHistory extends BaseModel{
  private Integer orderId;
  private String status;
  private Date updateDate;
  private String orderReferenceId;
  private Integer bookId;
  private String title;
  private String authorName;
  private Integer userId;
  private BigDecimal totalAmount;
  private Integer quantityOrdered;
}
