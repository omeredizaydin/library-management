package com.getir.lms.librarymanagement.model.transform;

import com.getir.lms.librarymanagement.dto.userinfo.UserInfoResponse;
import com.getir.lms.librarymanagement.model.entity.User;

public class AuthenticationAssembler {
  public static UserInfoResponse toResponse(User user) {
    return UserInfoResponse
        .builder()
        .firstName(user.getFirstName())
        .lastName(user.getLastName())
        .email(user.getEmail())
        .role(user.getRole())
        .build();
  }
}
