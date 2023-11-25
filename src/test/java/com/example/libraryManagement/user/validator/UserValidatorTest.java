package com.example.libraryManagement.user.validator;

import com.example.libraryManagement.mock.FakeContainer;
import com.example.libraryManagement.user.domain.User;
import com.example.libraryManagement.user.exception.UserException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.example.libraryManagement.common.response.error.ErrorType.*;
import static org.assertj.core.api.Assertions.*;

class UserValidatorTest {

    @ParameterizedTest
    @DisplayName("정상적인 이메일 입력 시")
    @ValueSource(strings = {"a@a.com", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa@aaaaa.com"})
    void validate(final String value) {
        FakeContainer fakeContainer = new FakeContainer();

        User user = User.builder()
                .email(value)
                .build();

        fakeContainer.userValidator.validateRegister(user);
    }

    @ParameterizedTest
    @DisplayName("올바르지 않은 형태의 이메일 입력 시 예외 발생")
    @ValueSource(strings = {"", "a.com", "a@a", "@a.com", "a@.com", "a@a."})
    void validateFail(final String value) {
        FakeContainer fakeContainer = new FakeContainer();

        User user = User.builder()
                .email(value)
                .build();
        assertThatThrownBy(() ->
                fakeContainer.userValidator.validateRegister(user)
        )
                .isInstanceOf(UserException.class)
                .hasMessage(USER_EMAIL_INVALID.getMessage());
    }

    @Test
    @DisplayName("이메일 중복 시 예외 발생")
    void validateFailedByDuplication() {
        FakeContainer fakeContainer = new FakeContainer();

        User existUser = User.builder()
                .email("a@a.com")
                .build();
        fakeContainer.userRepository.save(existUser);

        User newUser = User.builder()
                .email("a@a.com")
                .build();

        assertThatThrownBy(() ->
                fakeContainer.userValidator.validateRegister(newUser)
        )
                .isInstanceOf(UserException.class)
                .hasMessage(USER_EMAIL_DUPLICATION.getMessage());
    }
}