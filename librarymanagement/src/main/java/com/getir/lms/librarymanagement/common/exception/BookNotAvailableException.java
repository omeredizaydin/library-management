package com.getir.lms.librarymanagement.common.exception;

import lombok.Getter;

@Getter
public class BookNotAvailableException extends RuntimeException {
  private final String message;

  public BookNotAvailableException(String message) {
    super(message);
    this.message = message;
  }
}
