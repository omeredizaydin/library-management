package com.getir.lms.librarymanagement.controller;

import com.getir.lms.librarymanagement.dto.request.BookDto;
import com.getir.lms.librarymanagement.model.entity.Book;
import com.getir.lms.librarymanagement.service.book.BookService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class BookController {
  private final BookService bookService;

  @GetMapping("")
  @PreAuthorize("hasAnyAuthority('PATRON', 'LIBRARIAN')")
  public ResponseEntity<List<Book>> getAllBooks() {
    return ResponseEntity.ok(bookService.getAllBooks());
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasAnyAuthority('PATRON', 'LIBRARIAN')")
  public ResponseEntity<Book> getBookById(@PathVariable("id") UUID id) {
    return ResponseEntity.ok(bookService.getBookById(id));
  }

  @PostMapping("")
  @PreAuthorize("hasAuthority('LIBRARIAN')")
  public ResponseEntity<Book> create(@RequestBody BookDto dto) {
    return ResponseEntity.ok(bookService.create(dto));
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasAuthority('LIBRARIAN')")
  public ResponseEntity<Book> update(@PathVariable("id") UUID id, @RequestBody BookDto dto) {
    return ResponseEntity.ok(bookService.update(id, dto));
  }

  @GetMapping("/search")
  @PreAuthorize("hasAnyAuthority('PATRON', 'LIBRARIAN')")
  public Page<Book> searchBooks(
      @RequestParam(value = "term", required = false) String term,
      @RequestParam(value = "availability", required = false) Boolean availability,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size) {

    Pageable pageable = PageRequest.of(page, size);
    return bookService.searchBooks(term != null ? term : "", availability, pageable);
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasAuthority('LIBRARIAN')")
  public ResponseEntity<Book> update(@PathVariable("id") UUID id) {
    bookService.delete(id);
    return ResponseEntity.noContent().build();
  }

}
