package com.getir.lms.librarymanagement.service.book;

import com.getir.lms.librarymanagement.dto.BookDto;
import com.getir.lms.librarymanagement.model.entity.Book;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {
  List<Book> getAllBooks();

  Book getBookById(UUID id);

  Book create(BookDto dto);

  Book update(UUID id, BookDto dto);

  void delete(UUID id);

  Page<Book> searchBooks(String searchTerm, Boolean availability, Pageable pageable);
}
