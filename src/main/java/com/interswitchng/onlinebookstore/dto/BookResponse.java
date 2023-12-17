package com.interswitchng.onlinebookstore.dto;

import com.interswitchng.onlinebookstore.model.Author;
import com.interswitchng.onlinebookstore.model.BaseModel;
import com.interswitchng.onlinebookstore.model.Genre;
import lombok.Data;

@Data
public class BookResponse extends BaseModel {
  private String title;
  private String isbn;
  private Integer yearOfPublication;
  private Author author;
  private Genre genre;

}
