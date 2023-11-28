package com.example.libraryManagement.common.response.error;

public enum ErrorType {

    UNKNOWN_ERROR("정의되지 않은 에러입니다."),

    //    User
    USER_NOT_FOUND("해당 유저가 존재하지 않습니다."),
    USER_EMAIL_EMPTY("유저의 이메일은 필수입니다."),
    USER_EMAIL_INVALID("유저의 이메일이 올바르지 않은 형태입니다."),
    USER_EMAIL_DUPLICATION("유저의 이메일이 중복됩니다."),

    //    Book
    BOOK_NOT_FOUND("해당 책이 존재하지 않습니다."),
    BOOK_NAME_EMPTY("책의 이름은 필수입니다."),
    BOOK_ISBN_EMPTY("책의 ISBN은 필수입니다."),
    BOOK_ISBN_DUPLICATION("책의 ISBN이 중복됩니다."),
    BOOK_FORBIDDEN("권한이 없습니다."),
    BOOK_ALREADY_LENT("이미 대출이 된 책입니다."),

//    LentHistory
    LENT_HISTORY_NOT_FOUND("해당 대출 이력이 존재하지 않습니다.");

    private final String message;

    ErrorType(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
