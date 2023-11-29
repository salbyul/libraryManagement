package com.example.libraryManagement.book.dto.response;

import com.example.libraryManagement.book.domain.LentHistory;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class LentHistoryResponse {

    private String bookName;
    private String isbn;
    private String userEmail;
    private LocalDateTime lentDate;
    private LocalDateTime returnDate;

    public static LentHistoryResponse translate(final LentHistory lentHistory) {
        return LentHistoryResponse.builder()
                .bookName(lentHistory.getBook().getName())
                .isbn(lentHistory.getBook().getIsbn())
                .userEmail(lentHistory.getUser().getEmail())
                .lentDate(lentHistory.getLentDate())
                .returnDate(lentHistory.getReturnDate())
                .build();
    }
}
