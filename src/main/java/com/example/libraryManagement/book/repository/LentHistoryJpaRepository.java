package com.example.libraryManagement.book.repository;

import com.example.libraryManagement.book.domain.LentHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LentHistoryJpaRepository extends JpaRepository<LentHistory, Long> {
}
