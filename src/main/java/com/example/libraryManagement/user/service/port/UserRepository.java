package com.example.libraryManagement.user.service.port;

import com.example.libraryManagement.user.domain.User;

import java.util.Optional;

public interface UserRepository {

    User save(final User user);

    Optional<User> findById(final Long userId);

    Optional<User> findByEmail(final String email);
}
