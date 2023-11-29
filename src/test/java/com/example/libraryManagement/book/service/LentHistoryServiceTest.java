package com.example.libraryManagement.book.service;

import com.example.libraryManagement.book.domain.LentHistory;
import com.example.libraryManagement.book.dto.request.BookRegisterRequest;
import com.example.libraryManagement.book.dto.response.LentHistoryResponse;
import com.example.libraryManagement.book.exception.BookException;
import com.example.libraryManagement.mock.FakeContainer;
import com.example.libraryManagement.user.dto.request.UserRegisterRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.example.libraryManagement.common.response.error.ErrorType.*;
import static org.assertj.core.api.Assertions.*;

class LentHistoryServiceTest {

    @Test
    @DisplayName("성공적인 대출 이력 확인")
    void getLentHistoryByBookId() {
        FakeContainer fakeContainer = new FakeContainer();

//        유저 생성
        UserRegisterRequest userRegisterRequest = UserRegisterRequest.builder()
                .email("a@a.com")
                .build();
        fakeContainer.userService.register(userRegisterRequest);

//        도서 생성
        BookRegisterRequest bookRegisterRequest = BookRegisterRequest.builder()
                .name("너에게 하고 싶은 말")
                .isbn("10000")
                .build();
        Long savedBookId = fakeContainer.bookService.register(userRegisterRequest.getEmail(), bookRegisterRequest);

//        도서 대출
        fakeContainer.bookService.lend(userRegisterRequest.getEmail(), savedBookId);

//        검증
        List<LentHistoryResponse> lentHistoryList = fakeContainer.lentHistoryService.getLentHistoryByBookId(savedBookId);
        LentHistory lentHistory = fakeContainer.lentHistoryRepository.findByBookId(savedBookId).get(0);

        assertThat(lentHistoryList).size().isEqualTo(1);
        assertThat(lentHistoryList).extracting(LentHistoryResponse::getBookName).containsExactlyInAnyOrder("너에게 하고 싶은 말");
        assertThat(lentHistoryList).extracting(LentHistoryResponse::getIsbn).containsExactlyInAnyOrder("10000");
        assertThat(lentHistoryList).extracting(LentHistoryResponse::getUserEmail).containsExactlyInAnyOrder("a@a.com");
        assertThat(lentHistoryList).extracting(LentHistoryResponse::getLentDate).containsExactlyInAnyOrder(lentHistory.getLentDate());
        assertThat(lentHistoryList).extracting(LentHistoryResponse::getReturnDate).containsExactlyInAnyOrder(lentHistory.getReturnDate());
    }

    @Test
    @DisplayName("존재하지 않는 도서에 대한 대출 이력 확인 시 예외 발생")
    void getLentHistoryByBookIdFailedByNotExistBook() {
        FakeContainer fakeContainer = new FakeContainer();
        assertThatThrownBy(() ->
                fakeContainer.lentHistoryService.getLentHistoryByBookId(-1L)
        )
                .isInstanceOf(BookException.class)
                .hasMessage(BOOK_NOT_FOUND.getMessage());
    }
}