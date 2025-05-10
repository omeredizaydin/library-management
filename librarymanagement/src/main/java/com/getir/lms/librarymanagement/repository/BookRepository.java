package com.getir.lms.librarymanagement.repository;

import com.getir.lms.librarymanagement.model.entity.Book;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, UUID> {
  List<Book> getAllBooks();
}
