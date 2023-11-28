package com.example.libraryManagement.book.domain;

import com.example.libraryManagement.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "lent_history")
public class LentHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lent_history_id")
    private Long lentHistoryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    private User user;

    @Column(name = "lent_date")
    private LocalDateTime lentDate;

    @Column(name = "return_date")
    private LocalDateTime returnDate;

    @PrePersist
    public void prePersist() {
        this.lentDate = LocalDateTime.now();
    }
}
