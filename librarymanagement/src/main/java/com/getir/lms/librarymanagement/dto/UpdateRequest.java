package com.getir.lms.librarymanagement.dto;

import com.getir.lms.librarymanagement.model.enums.Role;
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
  private String email;
  private Role role;
}
