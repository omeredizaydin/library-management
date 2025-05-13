package com.getir.lms.librarymanagement.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.getir.lms.librarymanagement.common.exception.BookNotFoundException;
import com.getir.lms.librarymanagement.dto.request.BookDto;
import com.getir.lms.librarymanagement.model.entity.Book;
import com.getir.lms.librarymanagement.repository.BookRepository;
import com.getir.lms.librarymanagement.repository.UserRepository;
import com.getir.lms.librarymanagement.service.book.impl.BookServiceImpl;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

  @Mock
  private BookRepository bookRepository;

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private BookServiceImpl bookService;

  private Book book;
  private BookDto bookDto;
  private UUID bookId;

  @BeforeEach
  void setUp() {
    bookId = UUID.randomUUID();
    book = new Book();
    book.setId(bookId);
    book.setTitle("Test Book");
    book.setAuthor("Author");
    book.setIsbn("12345");
    book.setIsAvailable(true);
    book.setQuantity(5);

    bookDto = new BookDto();
    bookDto.setTitle("Test Book");
    bookDto.setAuthor("Author");
    bookDto.setIsbn("12345");
    bookDto.setIsAvailable(true);
    bookDto.setQuantity(5);
  }

  @Test
  void testGetAllBooks() {
    List<Book> books = List.of(book);
    when(bookRepository.findAll()).thenReturn(books);

    List<Book> result = bookService.getAllBooks();

    assertEquals(1, result.size());
    verify(bookRepository).findAll();
  }

  @Test
  void testGetBookById_found() {
    when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

    Book result = bookService.getBookById(bookId);

    assertEquals(book, result);
    verify(bookRepository).findById(bookId);
  }

  @Test
  void testGetBookById_notFound() {
    when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

    assertThrows(BookNotFoundException.class, () -> bookService.getBookById(bookId));
  }

  @Test
  void testCreateBook() {
    when(bookRepository.save(any(Book.class))).thenReturn(book);

    Book created = bookService.create(bookDto);

    assertEquals(book.getTitle(), created.getTitle());
    verify(bookRepository).save(any(Book.class));
  }

  @Test
  void testUpdateBook_found() {
    when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
    when(bookRepository.save(any(Book.class))).thenReturn(book);

    Book updated = bookService.update(bookId, bookDto);

    assertEquals(book.getTitle(), updated.getTitle());
    verify(bookRepository).findById(bookId);
    verify(bookRepository).save(book);
  }

  @Test
  void testUpdateBook_notFound() {
    when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

    assertThrows(BookNotFoundException.class, () -> bookService.update(bookId, bookDto));
  }

  @Test
  void testDeleteBook() {
    doNothing().when(bookRepository).deleteById(bookId);

    bookService.delete(bookId);

    verify(bookRepository).deleteById(bookId);
  }

  @Test
  void testSearchBooks() {
    Pageable pageable = PageRequest.of(0, 10);
    Page<Book> page = new PageImpl<>(List.of(book));

    when(bookRepository.searchBooks("test", pageable)).thenReturn(page);

    Page<Book> result = bookService.searchBooks("test", true, pageable);

    assertEquals(1, result.getTotalElements());
    verify(bookRepository).searchBooks("test", pageable);
  }
}