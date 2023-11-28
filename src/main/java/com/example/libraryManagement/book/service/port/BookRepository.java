package com.example.libraryManagement.book.service.port;

import com.example.libraryManagement.book.domain.Book;

import java.util.Optional;

public interface BookRepository {

    Book save(final Book book);

    Optional<Book> findByIsbn(final String isbn);

    Optional<Book> findById(final Long bookId);
}
