package com.getir.lms.librarymanagement.repository;

import com.getir.lms.librarymanagement.model.entity.Book;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, UUID> {
}
