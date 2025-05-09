package com.getir.lms.librarymanagement.service.auth;

import com.getir.lms.librarymanagement.dto.AuthenticationResponse;
import com.getir.lms.librarymanagement.dto.login.AuthenticationRequest;
import com.getir.lms.librarymanagement.dto.register.RegisterRequest;


public interface AuthenticationService {
   AuthenticationResponse register(RegisterRequest request);
   AuthenticationResponse authenticate(AuthenticationRequest request);


}
