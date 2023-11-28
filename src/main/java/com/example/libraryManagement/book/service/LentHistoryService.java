package com.example.libraryManagement.book.service;

import com.example.libraryManagement.book.domain.Book;
import com.example.libraryManagement.book.domain.LentHistory;
import com.example.libraryManagement.book.exception.BookException;
import com.example.libraryManagement.book.service.port.BookRepository;
import com.example.libraryManagement.book.service.port.LentHistoryRepository;
import com.example.libraryManagement.common.response.error.ErrorType;
import com.example.libraryManagement.user.domain.User;
import com.example.libraryManagement.user.service.port.UserRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Builder
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LentHistoryService {


}
