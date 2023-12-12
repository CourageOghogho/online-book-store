package com.interswitchng.onlinebookstore.model;

import java.util.Optional;
import java.util.stream.Stream;

public enum Genre {
  FRICTION("1", "Friction"),
  THRILLER("2","Thriller"),
  MYSTERY("3","Mystery"),
  POETRY("4","Poetry"),
  HORROR("5","Horror"),
  SATIRE("6","Satire");
  private final String code;
  private final String description;


  Genre(String code, String description) {
    this.code = code;
    this.description = description;
  }

  public String getCode() {
    return code;
  }

  public String getDescription() {
    return description;
  }

  public static String getDescription(String code) {
    return getDescriptionOrDefault(code, null);
  }

  public static String getDescriptionOrDefault(String code, String defaultName) {
    return Stream.of(values()).filter(value -> value.code.equalsIgnoreCase(code))
        .findFirst()
        .map(value -> value.description)
        .orElse(defaultName);
  }

  public static Optional<Genre> get(String code) {
    return Stream.of(values()).filter(v -> v.code.equalsIgnoreCase(code))
        .findFirst();
  }
}
