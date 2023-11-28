package com.example.libraryManagement.book.repository.adaptor;

import com.example.libraryManagement.book.domain.LentHistory;
import com.example.libraryManagement.book.repository.LentHistoryJpaRepository;
import com.example.libraryManagement.book.service.port.LentHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class LentHistoryRepositoryImpl implements LentHistoryRepository {

    private final LentHistoryJpaRepository lentHistoryJpaRepository;

    @Override
    public LentHistory save(final LentHistory lentHistory) {
        return lentHistoryJpaRepository.save(lentHistory);
    }

    @Override
    public Optional<LentHistory> findByLentHistoryId(final Long lentHistoryId) {
        return lentHistoryJpaRepository.findById(lentHistoryId);
    }

    @Override
    public List<LentHistory> findByBookId(final Long bookId) {
        return lentHistoryJpaRepository.findByBookId(bookId);
    }
}
