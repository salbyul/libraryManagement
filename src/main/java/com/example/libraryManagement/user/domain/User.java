package com.example.libraryManagement.user.domain;

import com.example.libraryManagement.book.domain.Book;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "users_id")
    private Long userId;

    private String email;

    @Column(name = "generated_date")
    private LocalDateTime generatedDate;

    @OneToMany(mappedBy = "registrant")
    private List<Book> bookList = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        this.generatedDate = LocalDateTime.now();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final User user = (User) o;
        return Objects.equals(getEmail(), user.getEmail()) && Objects.equals(getGeneratedDate(), user.getGeneratedDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEmail(), getGeneratedDate());
    }
}
