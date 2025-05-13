package com.getir.lms.librarymanagement.model.transform;

import com.getir.lms.librarymanagement.dto.request.BookDto;
import com.getir.lms.librarymanagement.dto.response.BorrowRecordDto;
import com.getir.lms.librarymanagement.dto.response.UserDto;
import com.getir.lms.librarymanagement.model.entity.Book;
import com.getir.lms.librarymanagement.model.entity.BorrowRecord;
import com.getir.lms.librarymanagement.model.entity.User;

public class BorrowRecordAssembler {
  private BorrowRecordAssembler() {

  }

  public static BorrowRecordDto toDto(BorrowRecord borrowRecord) {
    User user = borrowRecord.getUser();
    Book book = borrowRecord.getBook();

    UserDto userDto = new UserDto();
    userDto.setId(user.getId());
    userDto.setFirstName(user.getFirstName());
    userDto.setLastName(user.getLastName());
    userDto.setEmail(user.getEmail());

    BookDto bookDto = new BookDto();
    bookDto.setId(book.getId());
    bookDto.setTitle(book.getTitle());
    bookDto.setAuthor(book.getAuthor());
    bookDto.setIsbn(book.getIsbn());
    bookDto.setQuantity(book.getQuantity());
    bookDto.setIsAvailable(book.getIsAvailable());

    BorrowRecordDto dto = new BorrowRecordDto();
    dto.setId(String.valueOf(borrowRecord.getId()));
    dto.setIssueDate(borrowRecord.getIssueDate());
    dto.setDueDate(borrowRecord.getDueDate());
    dto.setIsReturned(borrowRecord.getIsReturned());
    dto.setReturnDate(borrowRecord.getReturnDate());
    dto.setUser(userDto);
    dto.setBook(bookDto);

    return dto;
  }
}
