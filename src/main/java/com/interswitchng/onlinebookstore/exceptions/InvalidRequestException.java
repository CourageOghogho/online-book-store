package com.interswitchng.onlinebookstore.exceptions;

public class InvalidRequestException extends RuntimeException {

  private String issue;
  private String description;
  public InvalidRequestException(String issue, String description) {
    this.issue=issue;
    this.description=description;
  }



}
