package com.getir.lms.librarymanagement.service.auth;

import com.getir.lms.librarymanagement.dto.response.AuthenticationResponse;
import com.getir.lms.librarymanagement.dto.request.UpdateRequest;
import com.getir.lms.librarymanagement.dto.response.UserInfoResponse;
import com.getir.lms.librarymanagement.dto.request.AuthenticationRequest;
import com.getir.lms.librarymanagement.dto.request.RegisterRequest;
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
