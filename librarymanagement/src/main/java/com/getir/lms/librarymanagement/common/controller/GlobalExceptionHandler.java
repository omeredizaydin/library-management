package com.getir.lms.librarymanagement.common.controller;

import com.getir.lms.librarymanagement.common.error.ErrorResponse;
import com.getir.lms.librarymanagement.common.exception.BookNotAvailableException;
import com.getir.lms.librarymanagement.common.exception.BookNotFoundException;
import com.getir.lms.librarymanagement.common.exception.BorrowRecordNotFound;
import com.getir.lms.librarymanagement.common.exception.UserNotFoundException;
import com.getir.lms.librarymanagement.common.error.ValidationErrorResponse;
import java.util.List;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ValidationErrorResponse> handleValidationExceptions(
      MethodArgumentNotValidException ex) {
    BindingResult bindingResult = ex.getBindingResult();

    // Collect all error messages from validation annotations
    List<String> errorMessages = bindingResult.getAllErrors().stream()
        .map(DefaultMessageSourceResolvable::getDefaultMessage).toList();

    // Create the error response object
    ValidationErrorResponse errorResponse =
        new ValidationErrorResponse("Validation failed", errorMessages);

    // Return the response with status BAD_REQUEST (400)
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);

  }

  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException ex) {
    ErrorResponse errorResponse = new ErrorResponse("User Not Found", ex.getMessage());
    return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(BookNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleBookNotFoundException(BookNotFoundException ex) {
    ErrorResponse errorResponse = new ErrorResponse("Book Not Found", ex.getMessage());
    return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(BorrowRecordNotFound.class)
  public ResponseEntity<ErrorResponse> handleBorrowRecordNotFoundException(
      BorrowRecordNotFound ex) {
    ErrorResponse errorResponse = new ErrorResponse("Borrow Not Found", ex.getMessage());
    return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(BookNotAvailableException.class)
  public ResponseEntity<ErrorResponse> handleBookNotAvailableException(
      BookNotAvailableException ex) {
    ErrorResponse errorResponse = new ErrorResponse("Book Not Available", ex.getMessage());
    return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
  }
}
