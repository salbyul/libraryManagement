package com.example.libraryManagement.mock.repository;

import com.example.libraryManagement.book.domain.LentHistory;
import com.example.libraryManagement.book.service.port.LentHistoryRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class LentHistoryFakeRepository implements LentHistoryRepository {

    private final Map<Long, LentHistory> data = new HashMap<>();
    private Long sequence = 1L;

    @Override
    public LentHistory save(final LentHistory lentHistory) {
        LentHistory entity = LentHistory.builder()
                .lentHistoryId(sequence++)
                .user(lentHistory.getUser())
                .book(lentHistory.getBook())
                .build();
        entity.prePersist();
        data.put(entity.getLentHistoryId(), entity);
        return entity;
    }

    @Override
    public List<LentHistory> findByBookId(final Long bookId) {
        return data.values().stream()
                .filter(lentHistory -> lentHistory.getBook().getBookId().equals(bookId))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<LentHistory> findNotReturnedByUserIdAndBookId(final Long userId, final Long bookId) {
        return data.values().stream()
                .filter(lentHistory ->
                        lentHistory.getUser().getUserId().equals(userId) &&
                        lentHistory.getBook().getBookId().equals(bookId) &&
                        lentHistory.getReturnDate() == null)
                .findFirst();
    }
}
