package com.getir.lms.librarymanagement.service.book;

import com.getir.lms.librarymanagement.model.entity.BorrowRecord;
import java.util.UUID;

public interface BorrowService {
  BorrowRecord borrow(UUID id);

  BorrowRecord returnBook(UUID id);
}
