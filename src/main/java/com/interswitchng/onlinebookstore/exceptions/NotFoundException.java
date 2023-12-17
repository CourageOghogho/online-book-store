package com.interswitchng.onlinebookstore.exceptions;

public class NotFoundException extends RuntimeException {

  private Integer code;
  private String description;
  public NotFoundException(Integer code, String description) {
    this.code=code;
    this.description=description;
  }
}
