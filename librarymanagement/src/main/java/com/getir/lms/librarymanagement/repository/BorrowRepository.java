package com.getir.lms.librarymanagement.repository;

import com.getir.lms.librarymanagement.model.entity.BorrowRecord;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BorrowRepository extends JpaRepository<BorrowRecord, UUID> {
  List<BorrowRecord> findAllByUserId(UUID userId);
}
