package com.example.libraryManagement.book.controller;

import com.example.libraryManagement.book.dto.response.LentHistoryResponse;
import com.example.libraryManagement.book.service.LentHistoryService;
import com.example.libraryManagement.common.response.ApiResponse;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@Builder
@RequiredArgsConstructor
@RequestMapping("/api/lent-history")
public class LentHistoryController {

    private final LentHistoryService lentHistoryService;

    /**
     * 대출 이력 확인 메서드
     * 특정 도서의 모든 대출 이력을 반환합니다.
     *
     * @param bookId 대출 이력을 확인할 도서의 PK
     * @return 대출 이력 리스트
     */
    @GetMapping("/books/{id}")
    public ApiResponse getLentHistoryByBookId(@PathVariable("id") final Long bookId) {
        log.info("getLentHistory bookId: {}", bookId);
        List<LentHistoryResponse> lentHistoryList = lentHistoryService.getLentHistoryByBookId(bookId);
        return ApiResponse.generate()
                .put("list", lentHistoryList);
    }
}
