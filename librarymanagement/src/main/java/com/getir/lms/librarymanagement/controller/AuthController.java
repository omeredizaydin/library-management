package com.getir.lms.librarymanagement.controller;

import com.getir.lms.librarymanagement.dto.response.AuthenticationResponse;
import com.getir.lms.librarymanagement.dto.request.UpdateRequest;
import com.getir.lms.librarymanagement.dto.response.UserInfoResponse;
import com.getir.lms.librarymanagement.dto.request.AuthenticationRequest;
import com.getir.lms.librarymanagement.dto.request.RegisterRequest;
import com.getir.lms.librarymanagement.service.auth.AuthenticationService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
  private final AuthenticationService service;

  @PostMapping("/register")
  public ResponseEntity<AuthenticationResponse> register(
      @Valid @RequestBody RegisterRequest request) {
    return ResponseEntity.ok(service.register(request));
  }

  @PostMapping("/authenticate")
  public ResponseEntity<AuthenticationResponse> login(
      @Valid @RequestBody AuthenticationRequest request) {
    return ResponseEntity.ok(service.authenticate(request));
  }

  @GetMapping("/me")
  @PreAuthorize("hasAnyAuthority('PATRON', 'LIBRARIAN')")
  public ResponseEntity<UserInfoResponse> getUserInfo() {
    return ResponseEntity.ok(service.getUserInfo());
  }

  @GetMapping("/details")
  @PreAuthorize("hasAuthority('LIBRARIAN')")
  public ResponseEntity<List<UserInfoResponse>> getAllUsers() {
    return ResponseEntity.ok(service.getAllUsers());
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasAuthority('LIBRARIAN')")
  public ResponseEntity<UserInfoResponse> updateUserInfo(@PathVariable UUID id,
                                                         @Valid @RequestBody
                                                         UpdateRequest request) {
    return ResponseEntity.ok(service.update(id, request));
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasAuthority('LIBRARIAN')")
  public ResponseEntity<Object> delete(@PathVariable UUID id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }
}
