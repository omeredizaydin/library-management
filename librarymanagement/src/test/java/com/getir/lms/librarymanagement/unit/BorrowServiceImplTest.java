package com.getir.lms.librarymanagement.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.getir.lms.librarymanagement.common.exception.BookNotAvailableException;
import com.getir.lms.librarymanagement.common.exception.BookNotFoundException;
import com.getir.lms.librarymanagement.dto.BorrowRecordDto;
import com.getir.lms.librarymanagement.model.entity.Book;
import com.getir.lms.librarymanagement.model.entity.BorrowRecord;
import com.getir.lms.librarymanagement.model.entity.User;
import com.getir.lms.librarymanagement.repository.BookRepository;
import com.getir.lms.librarymanagement.repository.BorrowRepository;
import com.getir.lms.librarymanagement.repository.UserRepository;
import com.getir.lms.librarymanagement.service.book.impl.BorrowServiceImpl;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

@ExtendWith(MockitoExtension.class)
class BorrowServiceImplTest {

  @Mock
  private BorrowRepository borrowRepository;

  @Mock
  private BookRepository bookRepository;

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private BorrowServiceImpl borrowService;

  private Book book;
  private User user;
  private BorrowRecord borrowRecord;

  @BeforeEach
  void setup() {
    book = new Book();
    book.setId(UUID.randomUUID());
    book.setQuantity(2);
    book.setIsAvailable(true);

    user = new User();
    user.setId(UUID.randomUUID());
    user.setEmail("test@example.com");

    borrowRecord = new BorrowRecord();
    borrowRecord.setId(UUID.randomUUID());
    borrowRecord.setBook(book);
    borrowRecord.setUser(user);
    borrowRecord.setIsReturned(false);
  }

  @Test
  void borrow_shouldSucceed_whenBookIsAvailable() {
    UUID bookId = book.getId();

    // Mock security context
    Authentication auth = mock(Authentication.class);
    SecurityContext securityContext = mock(SecurityContext.class);
    when(auth.getName()).thenReturn(user.getEmail());
    when(securityContext.getAuthentication()).thenReturn(auth);
    SecurityContextHolder.setContext(securityContext);

    when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
    when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
    when(borrowRepository.save(any(BorrowRecord.class))).thenReturn(borrowRecord);

    BorrowRecordDto result = borrowService.borrow(bookId);

    assertNotNull(result);
    verify(bookRepository).save(book);
    verify(borrowRepository).save(any(BorrowRecord.class));
  }

  @Test
  void borrow_shouldThrowException_whenBookNotFound() {
    UUID bookId = UUID.randomUUID();
    when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

    assertThrows(BookNotFoundException.class, () -> borrowService.borrow(bookId));
  }

  @Test
  void borrow_shouldThrowException_whenBookNotAvailable() {
    UUID bookId = book.getId();
    book.setQuantity(0);
    book.setIsAvailable(false);

    when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

    assertThrows(BookNotAvailableException.class, () -> borrowService.borrow(bookId));
  }

  @Test
  void returnBook_shouldSucceed_whenBookIsBorrowed() {
    borrowRecord.setIsReturned(false);
    UUID borrowId = borrowRecord.getId();

    when(borrowRepository.findById(borrowId)).thenReturn(Optional.of(borrowRecord));
    when(borrowRepository.save(any(BorrowRecord.class))).thenReturn(borrowRecord);

    BorrowRecordDto result = borrowService.returnBook(borrowId);

    assertNotNull(result);
    assertTrue(borrowRecord.getIsReturned());
    verify(bookRepository).save(book);
  }

  @Test
  void returnBook_shouldThrowException_whenAlreadyReturned() {
    borrowRecord.setIsReturned(true);
    UUID borrowId = borrowRecord.getId();

    when(borrowRepository.findById(borrowId)).thenReturn(Optional.of(borrowRecord));

    assertThrows(RuntimeException.class, () -> borrowService.returnBook(borrowId));
  }

  @Test
  void borrowHistory_shouldReturnUserHistory() {
    List<BorrowRecord> records = List.of(borrowRecord);

    // Mock SecurityContext
    Authentication auth = mock(Authentication.class);
    SecurityContext securityContext = mock(SecurityContext.class);
    when(auth.getName()).thenReturn(user.getEmail());
    when(securityContext.getAuthentication()).thenReturn(auth);
    SecurityContextHolder.setContext(securityContext);

    when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
    when(borrowRepository.findAllByUserId(user.getId())).thenReturn(records);

    List<BorrowRecordDto> result = borrowService.borrowHistory();

    assertNotNull(result);
    assertEquals(1, result.size());
  }

  @Test
  void borrowHistoryAllUsers_shouldReturnAllRecords() {
    List<BorrowRecord> records = List.of(borrowRecord);
    when(borrowRepository.findAll()).thenReturn(records);

    List<BorrowRecordDto> result = borrowService.borrowHistoryAllUsers();

    assertNotNull(result);
    assertEquals(1, result.size());
  }
}
