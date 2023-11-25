package com.example.libraryManagement.user.repository.adaptor;

import com.example.libraryManagement.user.domain.User;
import com.example.libraryManagement.user.repository.UserJpaRepository;
import com.example.libraryManagement.user.service.port.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    @Override
    public User save(final User user) {
        return userJpaRepository.save(user);
    }

    @Override
    public Optional<User> findById(final Long userId) {
        return userJpaRepository.findById(userId);
    }

    @Override
    public Optional<User> findByEmail(final String email) {
        return userJpaRepository.findByEmail(email);
    }
}
