package com.getir.lms.librarymanagement.service.auth.impl;

import com.getir.lms.librarymanagement.config.JwtService;
import com.getir.lms.librarymanagement.dto.AuthenticationResponse;
import com.getir.lms.librarymanagement.dto.login.AuthenticationRequest;
import com.getir.lms.librarymanagement.dto.register.RegisterRequest;
import com.getir.lms.librarymanagement.dto.userinfo.UserInfoResponse;
import com.getir.lms.librarymanagement.model.transform.AuthenticationAssembler;
import com.getir.lms.librarymanagement.service.auth.AuthenticationService;
import com.getir.lms.librarymanagement.model.enums.Role;
import com.getir.lms.librarymanagement.model.entity.User;
import com.getir.lms.librarymanagement.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  @Override
  @Transactional
  public AuthenticationResponse register(RegisterRequest request) {
    var user = User.builder()
        .firstName(request.getFirstName())
        .lastName(request.getLastName())
        .email(request.getEmail())
        .password(passwordEncoder.encode(request.getPassword()))
        .role(Role.PATRON)
        .build();
    userRepository.save(user);

    var jwtToken = jwtService.generateToken(user);
    return AuthenticationResponse.builder()
        .token(jwtToken)
        .build();
  }

  @Override
  public AuthenticationResponse authenticate(AuthenticationRequest request) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
    var user = userRepository.findByEmail(request.getEmail())
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    var jwtToken = jwtService.generateToken(user);
    return AuthenticationResponse.builder()
        .token(jwtToken)
        .build();
  }

  @Override
  public UserInfoResponse getUserInfo(Authentication authentication) {
    User user = userRepository.findByEmail(authentication.getName())
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    return AuthenticationAssembler.toResponse(user);
  }
}
