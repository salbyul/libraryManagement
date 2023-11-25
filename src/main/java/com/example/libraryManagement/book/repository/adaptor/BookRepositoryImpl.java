package com.example.libraryManagement.book.repository.adaptor;

import com.example.libraryManagement.book.domain.Book;
import com.example.libraryManagement.book.repository.BookJpaRepository;
import com.example.libraryManagement.book.service.port.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BookRepositoryImpl implements BookRepository {

    private final BookJpaRepository bookJpaRepository;

    @Override
    public Book save(final Book book) {
        return bookJpaRepository.save(book);
    }

    @Override
    public Optional<Book> findByIsbn(final String isbn) {
        return bookJpaRepository.findByIsbn(isbn);
    }
}
