package com.interswitchng.onlinebookstore.dto;

import com.interswitchng.onlinebookstore.model.Author;
import com.interswitchng.onlinebookstore.model.BaseModel;
import com.interswitchng.onlinebookstore.model.Genre;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class BookResponse extends BaseModel {
  private String title;
  private String isbn;
  private Integer yearOfPublication;
  private String authorName;
  private Integer authorId;
  private String genreName;
  private Integer genreId;
  private Integer availableCount;
  private BigDecimal price;

}
