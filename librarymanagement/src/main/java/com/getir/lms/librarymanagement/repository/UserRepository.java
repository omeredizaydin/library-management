package com.getir.lms.librarymanagement.repository;

import com.getir.lms.librarymanagement.model.entity.User;
import com.getir.lms.librarymanagement.model.enums.Role;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
  Optional<User> findByEmail(String email);

  List<User> findAllByRole(Role role);
}
