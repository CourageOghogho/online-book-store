//package com.interswitchng.onlinebookstore.exceptions;
//
//
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseStatus;
//
//@ControllerAdvice
//public class GlobalExceptionHandler {
//
//  @ExceptionHandler(ServiceLayerException.class)
//  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//  public ResponseEntity<ErrorResponse> handleServiceLayerException(ServiceLayerException ex) {
//    ErrorResponse errorResponse = new ErrorResponse("Service Layer Exception", ex.getDescription());
//    return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
//  }
//
//
//  @ExceptionHandler(Exception.class)
//  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//  public ResponseEntity<ErrorResponse> handleOtherExceptions(Exception ex) {
//    ErrorResponse errorResponse = new ErrorResponse("Internal Server Error", "An unexpected error occurred");
//    return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
//  }
//
//  public static class ErrorResponse {
//    private String error;
//    private String message;
//
//    public ErrorResponse(String error, String message) {
//      this.error = error;
//      this.message = message;
//    }
//    public String getError() {
//      return error;
//    }
//    public String getMessage() {
//      return message;
//    }
//  }
//}
