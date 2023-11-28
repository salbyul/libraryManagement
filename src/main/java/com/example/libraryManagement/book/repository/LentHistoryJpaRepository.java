package com.example.libraryManagement.book.repository;

import com.example.libraryManagement.book.domain.LentHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LentHistoryJpaRepository extends JpaRepository<LentHistory, Long> {

    @Query("SELECT lh FROM LentHistory lh WHERE lh.book.bookId = :bookId")
    List<LentHistory> findByBookId(@Param("bookId") final Long bookId);

    @Query("SELECT lh FROM LentHistory lh WHERE lh.book.bookId = :bookId AND lh.user.userId = :userId AND lh.returnDate IS NULL")
    Optional<LentHistory> findNotReturnedByUserIdAndBookId(@Param("userId") final Long userId,@Param("bookId") final Long bookId);
}
