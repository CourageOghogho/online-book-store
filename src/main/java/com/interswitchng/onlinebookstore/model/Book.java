package com.interswitchng.onlinebookstore.model;

import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

@Data
public class Book extends BaseModel{
  private String title;
  private Integer genreId;
  private String isbn;
  private Integer yearOfPublication;
  private Integer authorId;
  private Integer availableBookCount;
  private BigDecimal price;
}
