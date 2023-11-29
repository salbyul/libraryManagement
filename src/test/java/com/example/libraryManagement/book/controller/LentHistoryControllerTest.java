package com.example.libraryManagement.book.controller;

import com.example.libraryManagement.book.domain.LentHistory;
import com.example.libraryManagement.book.dto.request.BookRegisterRequest;
import com.example.libraryManagement.book.dto.response.LentHistoryResponse;
import com.example.libraryManagement.common.response.ApiResponse;
import com.example.libraryManagement.mock.FakeContainer;
import com.example.libraryManagement.user.dto.request.UserRegisterRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class LentHistoryControllerTest {

    @SuppressWarnings("unchecked")
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
        ApiResponse response = fakeContainer.lentHistoryController.getLentHistoryByBookId(savedBookId);
        List<LentHistoryResponse> lentHistoryList = (List<LentHistoryResponse>) response.getBody().get("list");
        LentHistory lentHistory = fakeContainer.lentHistoryRepository.findByBookId(savedBookId).get(0);

        assertThat(lentHistoryList).size().isEqualTo(1);
        assertThat(lentHistoryList).extracting(LentHistoryResponse::getBookName).containsExactlyInAnyOrder("너에게 하고 싶은 말");
        assertThat(lentHistoryList).extracting(LentHistoryResponse::getIsbn).containsExactlyInAnyOrder("10000");
        assertThat(lentHistoryList).extracting(LentHistoryResponse::getUserEmail).containsExactlyInAnyOrder("a@a.com");
        assertThat(lentHistoryList).extracting(LentHistoryResponse::getLentDate).containsExactlyInAnyOrder(lentHistory.getLentDate());
        assertThat(lentHistoryList).extracting(LentHistoryResponse::getReturnDate).containsExactlyInAnyOrder(lentHistory.getReturnDate());
    }
}