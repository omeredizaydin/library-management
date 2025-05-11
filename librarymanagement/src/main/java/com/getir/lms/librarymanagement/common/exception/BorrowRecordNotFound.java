package com.getir.lms.librarymanagement.common.exception;

import lombok.Getter;

@Getter
public class BorrowRecordNotFound extends RuntimeException {
  private final String message;
  public BorrowRecordNotFound(String message) {
    super(message);
    this.message = message;
  }
}
