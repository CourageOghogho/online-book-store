package com.interswitchng.onlinebookstore.dto;

import java.math.BigDecimal;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import lombok.Data;

@Data
public class BookRequest {

  @NotBlank(message = "Title cannot be blank")
  private String title;

  @NotNull(message = "Genre ID cannot be null")
  @Positive(message = "Genre ID must be a positive integer")
  private Integer genreId;

  @NotBlank(message = "ISBN cannot be blank")
  @Pattern(regexp = "^[0-9]{10,13}$", message = "ISBN must be a 10 to 13-digit number")
  private String isbn;

  @NotNull(message = "Year of publication cannot be null")
  @Positive(message = "Year of publication must be a positive integer")
  private Integer yearOfPublication;

  @Positive(message = "Author ID must be a positive integer")
  private Integer authorId;

  private String authorName;
  @Positive(message = "Initial count must be a positive integer")
  private Integer initialCount;

  private BigDecimal price;


}
