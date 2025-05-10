package com.getir.lms.librarymanagement.service.book;

import com.getir.lms.librarymanagement.dto.BookDto;
import com.getir.lms.librarymanagement.model.entity.Book;
import java.util.List;
import java.util.UUID;

public interface BookService {
  List<Book> getAllBooks();

  Book getBookById(UUID id);

  Book create(BookDto dto);

  Book update(UUID id, BookDto dto);

  void delete(UUID id);
}
