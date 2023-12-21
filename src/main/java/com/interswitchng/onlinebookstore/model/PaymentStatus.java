package com.interswitchng.onlinebookstore.model;

import java.util.Optional;
import java.util.stream.Stream;

public enum PaymentStatus {

  INITIATED(1,"Payment initiated"),
  SUCCESS(4,"Payment processed successfully"),
  In_PROGRESS(2,"Payment is being processed"),
  FAILED(3,"Payment Failed");
  private Integer code;
  private String description;

  PaymentStatus(Integer code, String description) {
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

  public static Optional<PaymentStatus> get(Integer code) {
    return Stream.of(values()).filter(v -> v.code.equals(code))
        .findFirst();
  }

}
