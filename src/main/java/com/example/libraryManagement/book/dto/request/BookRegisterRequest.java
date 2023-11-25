package com.example.libraryManagement.book.dto.request;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class BookRegisterRequest {

    private String registrantEmail;
    private String name;
    private String isbn;

    @Override
    public String toString() {
        return "registrantEmail: " + registrantEmail +
                "\t" +
                "name: " + name +
                "\t" +
                "isbn: " + isbn;
    }
}
