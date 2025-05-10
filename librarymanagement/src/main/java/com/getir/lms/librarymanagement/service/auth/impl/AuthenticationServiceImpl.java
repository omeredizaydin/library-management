package com.getir.lms.librarymanagement.service.auth.impl;

import com.getir.lms.librarymanagement.config.JwtService;
import com.getir.lms.librarymanagement.dto.AuthenticationResponse;
import com.getir.lms.librarymanagement.dto.UpdateRequest;
import com.getir.lms.librarymanagement.dto.login.AuthenticationRequest;
import com.getir.lms.librarymanagement.dto.register.RegisterRequest;
import com.getir.lms.librarymanagement.dto.userinfo.UserInfoResponse;
import com.getir.lms.librarymanagement.model.entity.User;
import com.getir.lms.librarymanagement.model.enums.Role;
import com.getir.lms.librarymanagement.model.transform.AuthenticationAssembler;
import com.getir.lms.librarymanagement.repository.UserRepository;
import com.getir.lms.librarymanagement.service.auth.AuthenticationService;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  @Override
  @Transactional
  public AuthenticationResponse register(RegisterRequest request) {
    log.debug(
        "AuthenticationServiceImpl::register user with  email: {} has been saving.",
        request.getEmail());
    var user = User.builder()
        .firstName(request.getFirstName())
        .lastName(request.getLastName())
        .email(request.getEmail())
        .password(passwordEncoder.encode(request.getPassword()))
        .role(request.getRole())
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
    log.debug(
        "AuthenticationServiceImpl::getUserInfo user information with email: {} has been fetching.",
        authentication.getName());
    User user = userRepository.findByEmail(authentication.getName())
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    return AuthenticationAssembler.toResponse(user);
  }

  @Override
  public List<UserInfoResponse> getAllUsers() {
    log.debug("AuthenticationServiceImpl::getAllUsers all patron users have been fetching.");
    List<User> allPatronUsers = userRepository.findAllByRole(Role.PATRON);
    return AuthenticationAssembler.toResponse(allPatronUsers);
  }

  @Override
  public UserInfoResponse update(UUID id, UpdateRequest request) {
    User user = userRepository.findById(id)
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    log.debug("AuthenticationServiceImpl::update user with id {} has been fetched.", user.getId());
    user.setFirstName(request.getFirstName());
    user.setLastName(request.getLastName());
    user.setEmail(request.getEmail());
    user.setRole(request.getRole());

    User updatedUser = userRepository.save(user);
    log.debug("AuthenticationServiceImpl::update user with id {} has been updated.", user.getId());

    return AuthenticationAssembler.toResponse(updatedUser);
  }

  @Override
  public void delete(UUID id) {
    userRepository.deleteById(id);
    log.debug("AuthenticationServiceImpl::delete user with id {} has been deleted.", id);
  }
}
