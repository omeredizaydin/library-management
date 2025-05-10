package com.getir.lms.librarymanagement.common.error;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ValidationErrorResponse {
  private String message;
  private List<String> errors;
}
