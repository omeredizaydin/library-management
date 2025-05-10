package com.getir.lms.librarymanagement.service.book.impl;

import com.getir.lms.librarymanagement.dto.BookDto;
import com.getir.lms.librarymanagement.model.entity.Book;
import com.getir.lms.librarymanagement.repository.BookRepository;
import com.getir.lms.librarymanagement.service.book.BookService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
  private final BookRepository bookRepository;

  @Override
  public List<Book> getAllBooks() {
    return bookRepository.getAllBooks();
  }

  @Override
  public Book getBookById(UUID id) {
    return bookRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Book not found"));
  }

  @Override
  public Book create(BookDto dto) {
    Book book = new Book();
    book.setTitle(dto.getTitle());
    book.setAuthor(dto.getAuthor());
    book.setIsbn(dto.getIsbn());
    book.setIsAvailable(dto.getIsAvailable());
    book.setQuantity(dto.getQuantity());

    return bookRepository.save(book);
  }

  @Override
  public Book update(UUID id, BookDto dto) {
    Book book =
        bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));

    book.setTitle(dto.getTitle());
    book.setAuthor(dto.getAuthor());
    book.setIsbn(dto.getIsbn());
    book.setIsAvailable(dto.getIsAvailable());
    book.setQuantity(dto.getQuantity());

    return bookRepository.save(book);
  }

  @Override
  public void delete(UUID id) {
    bookRepository.deleteById(id);
  }
}
