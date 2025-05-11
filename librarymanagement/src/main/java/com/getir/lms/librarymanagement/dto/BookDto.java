package com.getir.lms.librarymanagement.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.util.UUID;
import lombok.Data;

@Data
public class BookDto {
  private UUID id;
  @NotBlank(message = "Title is required")
  private String title;
  @NotBlank(message = "Author is required")
  @Pattern(regexp = ".*[a-zA-Z].*", message = "Author must contain letters and not a number")
  private String author;
  private String isbn;
  @Min(value = 0, message = "Quantity must be zero or a positive number")
  private Integer quantity;
  private Boolean isAvailable;
}
