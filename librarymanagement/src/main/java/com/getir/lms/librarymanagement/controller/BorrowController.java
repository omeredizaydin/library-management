package com.getir.lms.librarymanagement.controller;

import com.getir.lms.librarymanagement.model.entity.BorrowRecord;
import com.getir.lms.librarymanagement.service.book.BorrowService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
  public ResponseEntity<BorrowRecord> issueBook(@PathVariable UUID id){
    return ResponseEntity.ok(borrowService.borrow(id));
  }

  @PostMapping("/return/{id}")
  public ResponseEntity<BorrowRecord> returnBook(@PathVariable UUID id){
    return ResponseEntity.ok(borrowService.returnBook(id));
  }
}
