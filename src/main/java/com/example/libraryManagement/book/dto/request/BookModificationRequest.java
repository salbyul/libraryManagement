package com.example.libraryManagement.book.dto.request;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class BookModificationRequest {

    private String name;
    private String isbn;

    @Override
    public String toString() {
        return "BookModificationRequest{" +
                "name='" + name + '\'' +
                ", isbn='" + isbn + '\'' +
                '}';
    }
}
