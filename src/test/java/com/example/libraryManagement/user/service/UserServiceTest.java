package com.example.libraryManagement.user.service;

import com.example.libraryManagement.mock.FakeContainer;
import com.example.libraryManagement.user.domain.User;
import com.example.libraryManagement.user.dto.request.UserRegisterRequest;
import com.example.libraryManagement.user.exception.UserException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.example.libraryManagement.common.response.error.ErrorType.*;
import static org.assertj.core.api.Assertions.*;

class UserServiceTest {

    @Test
    @DisplayName("성공적인 회원가입")
    void register() {
        FakeContainer fakeContainer = new FakeContainer();

//        회원가입
        UserRegisterRequest request = UserRegisterRequest.builder()
                .email("example@example.com")
                .build();

        Long savedId = fakeContainer.userService.register(request);

//        검증
        User user = fakeContainer.userRepository.findById(savedId)
                .orElseThrow(() -> new UserException(USER_NOT_FOUND));

        assertThat(savedId).isEqualTo(user.getUserId());
        assertThat(user.getEmail()).isEqualTo("example@example.com");
    }
}