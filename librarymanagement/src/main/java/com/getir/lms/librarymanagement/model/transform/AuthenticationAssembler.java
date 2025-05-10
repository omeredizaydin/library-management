package com.getir.lms.librarymanagement.model.transform;

import com.getir.lms.librarymanagement.dto.info.UserInfoResponse;
import com.getir.lms.librarymanagement.model.entity.User;
import java.util.List;

public class AuthenticationAssembler {
  public static UserInfoResponse toResponse(User user) {
    return UserInfoResponse
        .builder()
        .id(user.getId())
        .firstName(user.getFirstName())
        .lastName(user.getLastName())
        .email(user.getEmail())
        .role(user.getRole())
        .build();
  }

  public static List<UserInfoResponse> toResponse(List<User> allPatronUsers) {
    return allPatronUsers.stream().map(AuthenticationAssembler::toResponse).toList();
  }
}
