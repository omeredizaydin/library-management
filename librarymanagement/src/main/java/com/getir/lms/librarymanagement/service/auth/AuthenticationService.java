package com.getir.lms.librarymanagement.service.auth;

import com.getir.lms.librarymanagement.dto.AuthenticationResponse;
import com.getir.lms.librarymanagement.dto.login.AuthenticationRequest;
import com.getir.lms.librarymanagement.dto.register.RegisterRequest;
import com.getir.lms.librarymanagement.dto.userinfo.UserInfoResponse;
import java.util.List;
import org.springframework.security.core.Authentication;


public interface AuthenticationService {
  AuthenticationResponse register(RegisterRequest request);

  AuthenticationResponse authenticate(AuthenticationRequest request);


  UserInfoResponse getUserInfo(Authentication authentication);

  List<UserInfoResponse> getAllUsers();
}
