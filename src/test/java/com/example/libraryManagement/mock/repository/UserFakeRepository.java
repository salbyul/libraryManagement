package com.example.libraryManagement.mock.repository;

import com.example.libraryManagement.user.domain.User;
import com.example.libraryManagement.user.service.port.UserRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class UserFakeRepository implements UserRepository {

    private final Map<Long, User> data = new HashMap<>();
    private Long sequence = 1L;

    @Override
    public User save(final User user) {
        user.prePersist();
        User entity = User.builder()
                .userId(sequence++)
                .email(user.getEmail())
                .generatedDate(user.getGeneratedDate())
                .build();
        data.put(entity.getUserId(), entity);
        return entity;
    }

    @Override
    public Optional<User> findById(final Long userId) {
        return Optional.ofNullable(data.getOrDefault(userId, null));
    }

    @Override
    public Optional<User> findByEmail(final String email) {
        return data.values().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst();
    }
}
