package com.interswitchng.onlinebookstore.model;

import java.util.Optional;
import java.util.stream.Stream;

public enum OrderStatus {
  INITIATED(1,"Order initiated"),
  SUCCESS(4,"Processed successfully"),
  In_PROGRESS(2,"Order is being processed"),
  FAILED(3,"Order Failed");
  private Integer code;
  private String description;

  OrderStatus(Integer code, String description) {
    this.code = code;
    this.description = description;
  }

  public Integer getCode() {
    return code;
  }

  public String getDescription() {
    return description;
  }


  public static String getDescription(Integer code) {
    return getDescriptionOrDefault(code, null);
  }

  public static String getDescriptionOrDefault(Integer code, String defaultName) {
    return Stream.of(values()).filter(value -> value.code.equals(code))
        .findFirst()
        .map(value -> value.description)
        .orElse(defaultName);
  }

  public static Optional<OrderStatus> get(Integer code) {
    return Stream.of(values()).filter(v -> v.code.equals(code))
        .findFirst();
  }
}
