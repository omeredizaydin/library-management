package com.getir.lms.librarymanagement.dto.userinfo;

import com.getir.lms.librarymanagement.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoResponse {
  private String firstName;
  private String lastName;
  private String email;
  private Role role;
}
