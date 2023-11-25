package com.example.libraryManagement.mock;

import com.example.libraryManagement.book.controller.BookController;
import com.example.libraryManagement.book.service.BookService;
import com.example.libraryManagement.book.service.port.BookRepository;
import com.example.libraryManagement.book.validator.BookValidator;
import com.example.libraryManagement.mock.repository.BookFakeRepository;
import com.example.libraryManagement.mock.repository.UserFakeRepository;
import com.example.libraryManagement.user.controller.UserController;
import com.example.libraryManagement.user.service.UserService;
import com.example.libraryManagement.user.service.port.UserRepository;
import com.example.libraryManagement.user.validator.UserValidator;

public class FakeContainer {

    public final UserService userService;
    public final UserRepository userRepository;
    public final UserController userController;
    public final UserValidator userValidator;

    public final BookService bookService;
    public final BookRepository bookRepository;
    public final BookController bookController;
    public final BookValidator bookValidator;

    public FakeContainer() {
//        Repository
        this.userRepository = new UserFakeRepository();
        this.bookRepository = new BookFakeRepository();

//        Validator
        this.userValidator = UserValidator.builder()
                .userRepository(this.userRepository)
                .build();
        this.bookValidator = BookValidator.builder()
                .bookRepository(this.bookRepository)
                .build();

//        Service
        this.userService = UserService.builder()
                .userRepository(userRepository)
                .userValidator(this.userValidator)
                .build();
        this.bookService = BookService.builder()
                .userRepository(this.userRepository)
                .bookRepository(this.bookRepository)
                .bookValidator(this.bookValidator)
                .build();

//        Controller
        this.userController = UserController.builder()
                .userService(this.userService)
                .build();
        this.bookController = BookController.builder()
                .bookService(this.bookService)
                .build();

    }
}
