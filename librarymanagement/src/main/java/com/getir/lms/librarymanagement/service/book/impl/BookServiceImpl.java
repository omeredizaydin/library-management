package com.getir.lms.librarymanagement.service.book.impl;

import com.getir.lms.librarymanagement.common.exception.BookNotFoundException;
import com.getir.lms.librarymanagement.dto.request.BookDto;
import com.getir.lms.librarymanagement.model.entity.Book;
import com.getir.lms.librarymanagement.repository.BookRepository;
import com.getir.lms.librarymanagement.repository.UserRepository;
import com.getir.lms.librarymanagement.service.book.BookService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
  private final BookRepository bookRepository;
  private final UserRepository userRepository;

  @Override
  public List<Book> getAllBooks() {
    log.info("BookServiceImpl::getAllBooks books are fetching...");
    return bookRepository.findAll();
  }

  @Override
  public Book getBookById(UUID id) {
    log.debug("BookServiceImpl::getBookById Book with user id: {} has been fetched", id);
    return bookRepository.findById(id)
        .orElseThrow(() -> new BookNotFoundException(String.format("given id: %s", id)));
  }

  @Override
  public Book create(BookDto dto) {
    log.debug("BookServiceImpl::create Book is getting created");
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
        bookRepository.findById(id)
            .orElseThrow(() -> new BookNotFoundException(String.format("given id: %s", id)));

    log.debug("BookServiceImpl::update Book is getting updating with id: {}", id);
    book.setTitle(dto.getTitle());
    book.setAuthor(dto.getAuthor());
    book.setIsbn(dto.getIsbn());
    book.setIsAvailable(dto.getIsAvailable());
    book.setQuantity(dto.getQuantity());

    return bookRepository.save(book);
  }

  @Override
  public void delete(UUID id) {
    log.debug("BookServiceImpl::delete Book is getting deleting with id: {}", id);
    bookRepository.deleteById(id);
  }

  @Override
  public Page<Book> searchBooks(String searchTerm, Boolean availability, Pageable pageable) {
    return bookRepository.searchBooks(searchTerm, pageable);
  }
}
