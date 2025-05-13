package com.getir.lms.librarymanagement.repository;

import com.getir.lms.librarymanagement.model.entity.Book;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, UUID> {

  @Query(value = "SELECT * FROM book b WHERE " +
      "(LOWER(b.title) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
      "LOWER(b.author) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
      "b.isbn LIKE LOWER(CONCAT('%', :searchTerm, '%')))",
      countQuery = "SELECT COUNT(*) FROM book b WHERE " +
          "(LOWER(b.title) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
          "LOWER(b.author) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
          "b.isbn LIKE LOWER(CONCAT('%', :searchTerm, '%')))",
      nativeQuery = true)
  Page<Book> searchBooks(String searchTerm, Pageable pageable);

}
