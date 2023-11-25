package com.example.libraryManagement.mock;

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

    public FakeContainer() {
//        Repository
        this.userRepository = new UserFakeRepository();

//        Validator
        this.userValidator = UserValidator.builder()
                .userRepository(this.userRepository)
                .build();

//        Service
        this.userService = UserService.builder()
                .userRepository(userRepository)
                .userValidator(this.userValidator)
                .build();

//        Controller
        this.userController = UserController.builder()
                .userService(this.userService)
                .build();

    }
}
