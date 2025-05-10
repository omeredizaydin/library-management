package com.getir.lms.librarymanagement.model.entity;

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
  private String title;
  private String author;
  private String isbn;
  private Integer quantity;
  private Boolean isAvailable;
}
