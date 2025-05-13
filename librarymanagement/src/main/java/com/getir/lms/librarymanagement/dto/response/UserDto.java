package com.getir.lms.librarymanagement.dto.response;

import java.util.UUID;
import lombok.Data;

@Data
public class UserDto {
  private UUID id;
  private String firstName;
  private String lastName;
  private String email;
}
