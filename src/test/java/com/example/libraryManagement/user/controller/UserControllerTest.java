package com.example.libraryManagement.user.controller;

import com.example.libraryManagement.common.response.ApiResponse;
import com.example.libraryManagement.common.response.error.ErrorType;
import com.example.libraryManagement.mock.FakeContainer;
import com.example.libraryManagement.user.domain.User;
import com.example.libraryManagement.user.dto.request.UserRegisterRequest;
import com.example.libraryManagement.user.exception.UserException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.example.libraryManagement.common.response.error.ErrorType.*;
import static org.assertj.core.api.Assertions.*;

class UserControllerTest {

    @Test
    @DisplayName("성공적인 회원가입")
    void register() {
        FakeContainer fakeContainer = new FakeContainer();

        UserRegisterRequest userRegisterRequest = new UserRegisterRequest("a@a.com");
        ApiResponse response = fakeContainer.userController.register(userRegisterRequest);
        User user = fakeContainer.userRepository.findByEmail("a@a.com")
                .orElseThrow(() -> new UserException(USER_NOT_FOUND));

        Long id = (Long) response.getBody().get("id");

        assertThat(user.getUserId()).isEqualTo(id);
    }
}