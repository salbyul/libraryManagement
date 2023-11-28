package com.example.libraryManagement.mock.repository;

import com.example.libraryManagement.book.domain.LentHistory;
import com.example.libraryManagement.book.service.port.LentHistoryRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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
}
