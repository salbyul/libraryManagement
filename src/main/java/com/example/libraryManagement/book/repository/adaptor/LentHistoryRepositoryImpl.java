package com.example.libraryManagement.book.repository.adaptor;

import com.example.libraryManagement.book.domain.LentHistory;
import com.example.libraryManagement.book.repository.LentHistoryJpaRepository;
import com.example.libraryManagement.book.service.port.LentHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class LentHistoryRepositoryImpl implements LentHistoryRepository {

    private final LentHistoryJpaRepository lentHistoryJpaRepository;

    @Override
    public LentHistory save(final LentHistory lentHistory) {
        return lentHistoryJpaRepository.save(lentHistory);
    }

    @Override
    public List<LentHistory> findByBookId(final Long bookId) {
        return lentHistoryJpaRepository.findByBookId(bookId);
    }
}
