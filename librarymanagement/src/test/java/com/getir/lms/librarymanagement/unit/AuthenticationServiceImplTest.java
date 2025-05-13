package com.getir.lms.librarymanagement.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.getir.lms.librarymanagement.security.JwtService;
import com.getir.lms.librarymanagement.dto.response.AuthenticationResponse;
import com.getir.lms.librarymanagement.dto.request.UpdateRequest;
import com.getir.lms.librarymanagement.dto.response.UserInfoResponse;
import com.getir.lms.librarymanagement.dto.request.AuthenticationRequest;
import com.getir.lms.librarymanagement.dto.request.RegisterRequest;
import com.getir.lms.librarymanagement.model.entity.User;
import com.getir.lms.librarymanagement.model.enums.Role;
import com.getir.lms.librarymanagement.repository.UserRepository;
import com.getir.lms.librarymanagement.service.auth.impl.AuthenticationServiceImpl;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceImplTest {

  @Mock
  private UserRepository userRepository;
  @Mock
  private PasswordEncoder passwordEncoder;
  @Mock
  private JwtService jwtService;
  @Mock
  private AuthenticationManager authenticationManager;

  @InjectMocks
  private AuthenticationServiceImpl authenticationService;

  @Test
  void testRegister_shouldSaveUserAndReturnToken() {
    RegisterRequest request =
        new RegisterRequest("John", "Doe", "john@example.com", "password", Role.PATRON);
    User savedUser = User.builder()
        .id(UUID.randomUUID())
        .firstName("John")
        .lastName("Doe")
        .email("john@example.com")
        .password("encodedPassword")
        .role(Role.PATRON)
        .build();

    when(passwordEncoder.encode(request.getPassword())).thenReturn("encodedPassword");
    when(userRepository.save(any(User.class))).thenReturn(savedUser);
    when(jwtService.generateToken(any(User.class))).thenReturn("jwt-token");

    AuthenticationResponse response = authenticationService.register(request);

    assertEquals("jwt-token", response.getToken());
    verify(userRepository).save(any(User.class));
  }

  @Test
  void testAuthenticate_shouldReturnToken() {
    AuthenticationRequest request = new AuthenticationRequest("john@example.com", "password");
    User user = User.builder()
        .email("john@example.com")
        .password("encodedPassword")
        .role(Role.PATRON)
        .build();

    when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(user));
    when(jwtService.generateToken(user)).thenReturn("jwt-token");

    AuthenticationResponse response = authenticationService.authenticate(request);

    assertEquals("jwt-token", response.getToken());
    verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
  }

  @Test
  void testGetUserInfo_shouldReturnUserInfo() {
    String email = "john@example.com";
    User user = User.builder()
        .email(email)
        .firstName("John")
        .lastName("Doe")
        .role(Role.PATRON)
        .build();

    SecurityContext securityContext = mock(SecurityContext.class);
    Authentication authentication = mock(Authentication.class);
    when(authentication.getName()).thenReturn(email);
    when(securityContext.getAuthentication()).thenReturn(authentication);
    SecurityContextHolder.setContext(securityContext);

    when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

    UserInfoResponse response = authenticationService.getUserInfo();

    assertEquals("john@example.com", response.getEmail());
  }

  @Test
  void testGetAllUsers_shouldReturnList() {
    List<User> users = List.of(
        User.builder().email("a@example.com").firstName("A").role(Role.PATRON).build(),
        User.builder().email("b@example.com").firstName("B").role(Role.PATRON).build()
    );

    when(userRepository.findAllByRole(Role.PATRON)).thenReturn(users);

    List<UserInfoResponse> result = authenticationService.getAllUsers();

    assertEquals(2, result.size());
    assertEquals("a@example.com", result.get(0).getEmail());
  }

  @Test
  void testUpdate_shouldUpdateUser() {
    UUID userId = UUID.randomUUID();
    UpdateRequest request = new UpdateRequest("New", "Name", "new@example.com", Role.LIBRARIAN);
    User existingUser = User.builder()
        .id(userId)
        .firstName("Old")
        .lastName("User")
        .email("old@example.com")
        .role(Role.PATRON)
        .build();

    when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
    when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArgument(0));

    UserInfoResponse updated = authenticationService.update(userId, request);

    assertEquals("new@example.com", updated.getEmail());
    assertEquals("New", updated.getFirstName());
    assertEquals(Role.LIBRARIAN, updated.getRole());
  }

  @Test
  void testDelete_shouldCallDeleteById() {
    UUID id = UUID.randomUUID();

    authenticationService.delete(id);

    verify(userRepository).deleteById(id);
  }
}

