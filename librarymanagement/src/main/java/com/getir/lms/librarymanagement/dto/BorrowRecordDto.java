package com.getir.lms.librarymanagement.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class BorrowRecordDto {
  private String id;
  private LocalDate issueDate;
  private LocalDate dueDate;
  private LocalDate returnDate;
  private Boolean isReturned;
  private UserDto user;
  private BookDto book;

}
