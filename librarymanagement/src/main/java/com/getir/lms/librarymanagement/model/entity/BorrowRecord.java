package com.getir.lms.librarymanagement.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.UUID;
import lombok.Data;

@Entity
@Data
@Table(name = "borrow_records")
public class BorrowRecord {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;
  private LocalDate issueDate;
  private LocalDate dueDate;
  private LocalDate returnDate;
  private Boolean isReturned;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne
  @JoinColumn(name = "book_id")
  private Book book;
}
