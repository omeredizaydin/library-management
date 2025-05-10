package com.getir.lms.librarymanagement.service.book.impl;

import com.getir.lms.librarymanagement.dto.BookDto;
import com.getir.lms.librarymanagement.model.entity.Book;
import com.getir.lms.librarymanagement.service.book.BookService;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {
  @Override
  public List<Book> getAllBooks() {
    return List.of();
  }

  @Override
  public Book getBookById(UUID id) {
    return null;
  }

  @Override
  public Book create(BookDto dto) {
    return null;
  }

  @Override
  public Book update(UUID id, BookDto dto) {
    return null;
  }

  @Override
  public void delete(UUID id) {

  }
}
