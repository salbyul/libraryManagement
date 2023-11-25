package com.example.libraryManagement.user.service;

import com.example.libraryManagement.user.dto.request.UserRegisterRequest;
import com.example.libraryManagement.user.domain.User;
import com.example.libraryManagement.user.service.port.UserRepository;
import com.example.libraryManagement.user.validator.UserValidator;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Builder
public class UserService {

    private final UserRepository userRepository;
    private final UserValidator userValidator;

    @Transactional
    public Long register(final UserRegisterRequest userRegisterRequest) {
        User user = User.builder()
                .email(userRegisterRequest.getEmail())
                .build();
        userValidator.validateRegister(user);
        return userRepository.save(user).getUserId();
    }
}
