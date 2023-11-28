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
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long bookId;

    private String name;
    private String isbn;

    @Enumerated(value = EnumType.STRING)
    private BookState state;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    private User registrant;

    @Column(name = "generated_date")
    private LocalDateTime generatedDate;

    @Column(name = "modified_date")
    private LocalDateTime modifiedDate;

    public boolean isRightUser(final User user) {
        return registrant.equals(user);
    }

    public void update(final String name, final String isbn) {
        this.name = name;
        this.isbn = isbn;
    }

    @PrePersist
    public void prePersist() {
        this.generatedDate = LocalDateTime.now();
        this.modifiedDate = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.modifiedDate = LocalDateTime.now();
    }
}
