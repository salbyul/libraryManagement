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
    public Optional<LentHistory> findByLentHistoryId(final Long lentHistoryId) {
        return data.values().stream()
                .filter(l -> l.getLentHistoryId().equals(lentHistoryId))
                .findFirst();
    }

    @Override
    public List<LentHistory> findByBookId(final Long bookId) {
        return data.values().stream()
                .filter(lentHistory -> lentHistory.getBook().getBookId().equals(bookId))
                .collect(Collectors.toList());
    }
}
