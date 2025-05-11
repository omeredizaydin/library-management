package com.getir.lms.librarymanagement.controller;

import com.getir.lms.librarymanagement.dto.BorrowRecordDto;
import com.getir.lms.librarymanagement.service.book.BorrowService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class BorrowController {
  private final BorrowService borrowService;

  @PostMapping("/borrow/{id}")
  @PreAuthorize("hasAuthority('PATRON')")
  public ResponseEntity<BorrowRecordDto> issueBook(@PathVariable UUID id) {
    return ResponseEntity.ok(borrowService.borrow(id));
  }

  @PostMapping("/return/{id}")
  @PreAuthorize("hasAuthority('PATRON')")
  public ResponseEntity<BorrowRecordDto> returnBook(@PathVariable UUID id) {
    return ResponseEntity.ok(borrowService.returnBook(id));
  }

  @GetMapping("/history/me")
  @PreAuthorize("hasAuthority('PATRON')")
  public ResponseEntity<List<BorrowRecordDto>> getBorrowHistory() {
    return ResponseEntity.ok(borrowService.borrowHistory());
  }

  @GetMapping("/history")
  @PreAuthorize("hasAuthority('LIBRARIAN')")
  public ResponseEntity<List<BorrowRecordDto>> getAllUsersBorrowHistory() {
    return ResponseEntity.ok(borrowService.borrowHistoryAllUsers());
  }
}
