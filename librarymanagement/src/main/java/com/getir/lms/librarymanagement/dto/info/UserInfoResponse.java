package com.getir.lms.librarymanagement.dto.info;

import com.getir.lms.librarymanagement.model.enums.Role;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoResponse {
  private UUID id;
  private String firstName;
  private String lastName;
  private String email;
  private Role role;
}
