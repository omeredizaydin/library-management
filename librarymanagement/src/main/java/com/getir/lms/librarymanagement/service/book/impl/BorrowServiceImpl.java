package com.getir.lms.librarymanagement.service.book.impl;

import com.getir.lms.librarymanagement.common.exception.UserNotFoundException;
import com.getir.lms.librarymanagement.model.entity.Book;
import com.getir.lms.librarymanagement.model.entity.BorrowRecord;
import com.getir.lms.librarymanagement.model.entity.User;
import com.getir.lms.librarymanagement.repository.BookRepository;
import com.getir.lms.librarymanagement.repository.BorrowRepository;
import com.getir.lms.librarymanagement.repository.UserRepository;
import com.getir.lms.librarymanagement.service.book.BorrowService;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
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
  public BorrowRecord borrow(UUID id) {
    Book book = bookRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Book not found"));

    checkAvailability(book);

    User user = getCurrentUser();

    BorrowRecord borrowRecord = createBorrowRecord(user, book);

    book.setQuantity(book.getQuantity() - 1);
    if (book.getQuantity() == 0) {
      book.setIsAvailable(false);
    }

    bookRepository.save(book);
    return borrowRepository.save(borrowRecord);
  }

  @Override
  @Transactional
  public BorrowRecord returnBook(UUID id) {
    BorrowRecord borrowRecord =
        borrowRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Borrow record not found"));

    if (borrowRecord.getIsReturned()) {
      throw new RuntimeException("Book is already returned");
    }

    Book book = borrowRecord.getBook();
    book.setQuantity(book.getQuantity() + 1);
    book.setIsAvailable(true);
    bookRepository.save(book);

    borrowRecord.setReturnDate(LocalDate.now());
    borrowRecord.setIsReturned(true);

    return borrowRepository.save(borrowRecord);
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
      throw new RuntimeException("Book is not available");
    }
  }

  private User getCurrentUser() {
    String email = SecurityContextHolder.getContext().getAuthentication().getName();
    return userRepository.findByEmail(email)
        .orElseThrow(() -> new UserNotFoundException("User not found"));
  }
}
