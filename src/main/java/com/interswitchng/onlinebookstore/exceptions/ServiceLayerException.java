package com.interswitchng.onlinebookstore.exceptions;

import lombok.Data;

@Data
public class ServiceLayerException extends RuntimeException {

  private String description;
  public ServiceLayerException(String description) {
    this.description=description;
  }
}
