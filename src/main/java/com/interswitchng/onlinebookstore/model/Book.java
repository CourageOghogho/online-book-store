package com.interswitchng.onlinebookstore.model;

import lombok.Data;

@Data
public class Book extends BaseModel{
  private String title;
  private Genre genre;
  private String isbn;
  private String author;
  private int yearOfPublication;
}
