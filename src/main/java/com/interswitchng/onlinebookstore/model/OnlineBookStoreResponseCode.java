package com.interswitchng.onlinebookstore.model;

public enum OnlineBookStoreResponseCode {
  SUCCESS("20000","Operation successful"),
  FAILED("50001","Operation not successful");
  private String code;
  private String description;


  OnlineBookStoreResponseCode(String code, String description) {
    this.code = code;
    this.description = description;
  }

  public String getCode() {
    return code;
  }

  public String getDescription() {
    return description;
  }
}
