package com.interswitchng.onlinebookstore.dto;

import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthorRequest {
  @NotBlank(message = "Author's name cannot be blank")
  private String authorName;

}
