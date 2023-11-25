package com.example.libraryManagement.user.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
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
