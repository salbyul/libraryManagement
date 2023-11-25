package com.example.libraryManagement.mock.repository;

import com.example.libraryManagement.book.domain.Book;
import com.example.libraryManagement.book.domain.BookState;
import com.example.libraryManagement.book.service.port.BookRepository;
import com.example.libraryManagement.user.domain.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class BookFakeRepository implements BookRepository {

    private final Map<Long, Book> data = new HashMap<>();
    private Long sequence = 1L;

    @Override
    public Book save(final Book book) {
        book.prePersist();
        User registrant = book.getRegistrant();
        Book entity = Book.builder()
                .bookId(sequence++)
                .name(book.getName())
                .isbn(book.getIsbn())
                .state(BookState.LENDABLE)
                .registrant(registrant)
                .generatedDate(book.getGeneratedDate())
                .modifiedDate(book.getModifiedDate())
                .build();
        registrant.getBookList().add(entity);
        data.put(entity.getBookId(), entity);
        return entity;
    }

    @Override
    public Optional<Book> findByIsbn(final String isbn) {
        return data.values().stream()
                .filter(book -> book.getIsbn().equals(isbn))
                .findFirst();
    }
}
