package com.getir.lms.librarymanagement.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ErrorResponse {
  private String message;
  private String details;
}
