package com.getir.lms.librarymanagement.dto;

import com.getir.lms.librarymanagement.model.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRequest {
  private String firstName;
  private String lastName;
  @Email(message = "Invalid email format")
  private String email;
  @NotNull(message = "Role is required")
  private Role role;
}
