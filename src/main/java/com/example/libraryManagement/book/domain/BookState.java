package com.example.libraryManagement.book.domain;

public enum BookState {
    LENT, LENDABLE;

    public boolean isLent() {
        return this == LENT;
    }
}
