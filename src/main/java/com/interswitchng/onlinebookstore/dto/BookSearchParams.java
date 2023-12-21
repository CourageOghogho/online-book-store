package com.interswitchng.onlinebookstore.dto;

import lombok.Data;

@Data
public class BookSearchParams extends PageParams{

  private String title;
  private String isbn;
  private String authorName;
  private Integer yearOfPublication;
  private Integer genreId;
  private Integer genreName;

}
