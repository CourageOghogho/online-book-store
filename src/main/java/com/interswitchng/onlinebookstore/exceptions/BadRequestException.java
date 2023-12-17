package com.interswitchng.onlinebookstore.exceptions;

import lombok.Data;

@Data
public class BadRequestException extends RuntimeException{
  private String issue;
  private String description;
  public BadRequestException(String issue, String description) {
    this.issue=issue;
    this.description=description;
  }

}
