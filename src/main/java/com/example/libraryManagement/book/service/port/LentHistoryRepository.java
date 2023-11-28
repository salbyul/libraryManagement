package com.example.libraryManagement.book.service.port;

import com.example.libraryManagement.book.domain.LentHistory;

import java.util.List;

public interface LentHistoryRepository {

    LentHistory save(final LentHistory lentHistory);

    List<LentHistory> findByBookId(final Long bookId);
}
