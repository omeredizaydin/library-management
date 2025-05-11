package com.getir.lms.librarymanagement.service.auth;

import com.getir.lms.librarymanagement.dto.AuthenticationResponse;
import com.getir.lms.librarymanagement.dto.UpdateRequest;
import com.getir.lms.librarymanagement.dto.info.UserInfoResponse;
import com.getir.lms.librarymanagement.dto.login.AuthenticationRequest;
import com.getir.lms.librarymanagement.dto.register.RegisterRequest;
import java.util.List;
import java.util.UUID;


public interface AuthenticationService {
  AuthenticationResponse register(RegisterRequest request);

  AuthenticationResponse authenticate(AuthenticationRequest request);

  UserInfoResponse getUserInfo();

  List<UserInfoResponse> getAllUsers();

  UserInfoResponse update(UUID id, UpdateRequest request);

  void delete(UUID id);
}
