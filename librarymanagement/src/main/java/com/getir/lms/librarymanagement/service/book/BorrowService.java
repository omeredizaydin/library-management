package com.getir.lms.librarymanagement.service.book;

import com.getir.lms.librarymanagement.dto.BorrowRecordDto;
import java.util.List;
import java.util.UUID;

public interface BorrowService {
  BorrowRecordDto borrow(UUID id);

  BorrowRecordDto returnBook(UUID id);

  List<BorrowRecordDto> borrowHistory();

  List<BorrowRecordDto> borrowHistoryAllUsers();
}
