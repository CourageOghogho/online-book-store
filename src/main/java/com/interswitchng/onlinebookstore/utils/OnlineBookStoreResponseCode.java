package com.interswitchng.onlinebookstore.utils;

import java.util.Optional;
import java.util.stream.Stream;

public enum OnlineBookStoreResponseCode {
  ENTITY_NOT_FOUND(40004, "Entity Not Found");
  private Integer code;
  private String description;

  OnlineBookStoreResponseCode(Integer code, String description) {
    this.code = code;
    this.description = description;
  }

  public Integer getCode() {
    return code;
  }

  public String getDescription() {
    return description;
  }


  public static String getDescriptionOrDefault(Integer code, String defaultName) {
    return Stream.of(values()).filter(value -> value.code.equals(code))
        .findFirst()
        .map(value -> value.description)
        .orElse(defaultName);
  }

  public static Optional<OnlineBookStoreResponseCode> get(Integer code) {
    return Stream.of(values()).filter(v -> v.code.equals(code))
        .findFirst();
  }


}
