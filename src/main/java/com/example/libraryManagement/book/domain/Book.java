package com.example.libraryManagement.book.domain;

import com.example.libraryManagement.user.domain.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.time.LocalDateTime;
import java.util.Objects;

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

    @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        final Book book = (Book) o;
        return getBookId() != null && Objects.equals(getBookId(), book.getBookId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBookId(), getName(), getIsbn(), getState(), getRegistrant(), getGeneratedDate(), getModifiedDate());
    }
}
