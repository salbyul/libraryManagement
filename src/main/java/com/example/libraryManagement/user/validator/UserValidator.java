package com.example.libraryManagement.user.validator;

import com.example.libraryManagement.common.annotation.Validator;
import com.example.libraryManagement.user.domain.User;
import com.example.libraryManagement.user.exception.UserException;
import com.example.libraryManagement.user.service.port.UserRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.Optional;

import static com.example.libraryManagement.common.response.error.ErrorType.*;

@Validator
@Builder
@RequiredArgsConstructor
public class UserValidator {

    private final UserRepository userRepository;

    public void validateRegister(final User user) {
        validateEmail(user.getEmail());
    }

    private void validateEmail(final String email) {
        if (isEmpty(email)) {
            throw new UserException(USER_EMAIL_EMPTY);
        }
        String regex = "^[a-zA-Z0-9+-_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$";
        if (!email.matches(regex)) {
            throw new UserException(USER_EMAIL_INVALID);
        }
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            throw new UserException(USER_EMAIL_DUPLICATION);
        }
    }

    private boolean isEmpty(final String value) {
        return !StringUtils.hasText(value);
    }
}
