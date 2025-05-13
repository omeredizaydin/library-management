package com.getir.lms.librarymanagement.service.book.impl;

import com.getir.lms.librarymanagement.common.exception.BookNotAvailableException;
import com.getir.lms.librarymanagement.common.exception.BookNotFoundException;
import com.getir.lms.librarymanagement.common.exception.BorrowRecordNotFound;
import com.getir.lms.librarymanagement.common.exception.UserNotFoundException;
import com.getir.lms.librarymanagement.dto.response.BorrowRecordDto;
import com.getir.lms.librarymanagement.model.entity.Book;
import com.getir.lms.librarymanagement.model.entity.BorrowRecord;
import com.getir.lms.librarymanagement.model.entity.User;
import com.getir.lms.librarymanagement.model.transform.BorrowRecordAssembler;
import com.getir.lms.librarymanagement.repository.BookRepository;
import com.getir.lms.librarymanagement.repository.BorrowRepository;
import com.getir.lms.librarymanagement.repository.UserRepository;
import com.getir.lms.librarymanagement.service.book.BorrowService;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class BorrowServiceImpl implements BorrowService {
  private final BorrowRepository borrowRepository;
  private final BookRepository bookRepository;
  private final UserRepository userRepository;

  @Override
  @Transactional
  public BorrowRecordDto borrow(UUID id) {
    Book book = bookRepository.findById(id)
        .orElseThrow(() -> new BookNotFoundException(String.format("given id: %s", id)));

    checkAvailability(book);

    User user = getCurrentUser();

    BorrowRecord borrowRecord = createBorrowRecord(user, book);

    book.setQuantity(book.getQuantity() - 1);
    if (book.getQuantity() == 0) {
      book.setIsAvailable(false);
    }

    bookRepository.save(book);
    return BorrowRecordAssembler.toDto(borrowRepository.save(borrowRecord));
  }

  @Override
  @Transactional
  public BorrowRecordDto returnBook(UUID id) {
    BorrowRecord borrowRecord =
        borrowRepository.findById(id)
            .orElseThrow(() -> new BorrowRecordNotFound(String.format("given id: %s", id)));

    if (Boolean.TRUE.equals(borrowRecord.getIsReturned())) {
      throw new RuntimeException("Book is already returned");
    }

    Book book = borrowRecord.getBook();
    book.setQuantity(book.getQuantity() + 1);
    book.setIsAvailable(true);
    bookRepository.save(book);

    borrowRecord.setReturnDate(LocalDate.now());
    borrowRecord.setIsReturned(true);

    return BorrowRecordAssembler.toDto(borrowRepository.save(borrowRecord));
  }

  @Override
  public List<BorrowRecordDto> borrowHistory() {
    User user = getCurrentUser();
    return borrowRepository.findAllByUserId(user.getId()).stream().map(BorrowRecordAssembler::toDto)
        .toList();
  }

  @Override
  public List<BorrowRecordDto> borrowHistoryAllUsers() {
    return borrowRepository.findAll().stream().map(BorrowRecordAssembler::toDto).toList();
  }

  private static BorrowRecord createBorrowRecord(User user, Book book) {
    BorrowRecord borrowRecord = new BorrowRecord();
    borrowRecord.setIssueDate(LocalDate.now());
    borrowRecord.setDueDate(LocalDate.now().plusDays(7));
    borrowRecord.setIsReturned(false);
    borrowRecord.setUser(user);
    borrowRecord.setBook(book);
    return borrowRecord;
  }

  private static void checkAvailability(Book book) {
    if (book.getQuantity() <= 0 || !book.getIsAvailable()) {
      throw new BookNotAvailableException(
          String.format("given id: %s with status: %s and quantity: %s", book.getId(),
              book.getIsAvailable(), book.getQuantity()));
    }
  }

  private User getCurrentUser() {
    String email = SecurityContextHolder.getContext().getAuthentication().getName();
    return userRepository.findByEmail(email)
        .orElseThrow(() -> new UserNotFoundException(String.format("given email: %s", email)));
  }
}
