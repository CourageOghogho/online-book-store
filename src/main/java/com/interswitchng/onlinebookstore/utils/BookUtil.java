package com.interswitchng.onlinebookstore.utils;

import com.interswitchng.onlinebookstore.exceptions.InvalidRequestException;
import java.util.regex.Pattern;

public class BookUtil {

  public static void bookTitleValidator(String title){
    if (!Pattern.matches("[a-zA-Z0-9]+", title)) {
      throw new InvalidRequestException("Book title", "Book title must contain only letters and numbers");
    }
  }

  public static void bookIsbnValidator(String isbn){
    if (!Pattern.matches("[0-9\\-]+", isbn)) {
      throw new InvalidRequestException("Book ISBN", "Book ISBN must contain only numbers and dash(-)");
    }
  }

}
