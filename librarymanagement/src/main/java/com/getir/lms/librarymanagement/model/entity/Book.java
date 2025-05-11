package com.getir.lms.librarymanagement.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.UUID;
import lombok.Data;

@Entity
@Data
public class Book {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;
  @Column(name = "title", columnDefinition = "VARCHAR(255)")
  private String title;
  @Column(name = "author", columnDefinition = "VARCHAR(255)")
  private String author;
  @Column(name = "isbn", columnDefinition = "VARCHAR(255)")
  private String isbn;
  @Column(name = "quantity")
  private Integer quantity;
  @Column(name = "is_available")
  private Boolean isAvailable;
}
