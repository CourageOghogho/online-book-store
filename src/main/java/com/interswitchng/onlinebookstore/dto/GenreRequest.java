package com.interswitchng.onlinebookstore.dto;

import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class GenreRequest {
  @NotBlank(message = "Genre's name cannot be blank")
  private String genreName;

}
