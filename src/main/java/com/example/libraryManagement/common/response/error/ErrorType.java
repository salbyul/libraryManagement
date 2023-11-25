package com.example.libraryManagement.common.response.error;

public enum ErrorType {

    UNKNOWN_ERROR("정의되지 않은 에러입니다."),
//    User
    USER_NOT_FOUND("해당 유저가 존재하지 않습니다."),
    USER_EMAIL_INVALID("유저의 이메일이 올바르지 않은 형태입니다."),
    USER_EMAIL_DUPLICATION("유저의 이메일이 중복됩니다.");

    private final String message;

    ErrorType(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
