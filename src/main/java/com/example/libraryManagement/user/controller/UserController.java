package com.example.libraryManagement.user.controller;

import com.example.libraryManagement.common.response.ApiResponse;
import com.example.libraryManagement.user.dto.request.UserRegisterRequest;
import com.example.libraryManagement.user.service.UserService;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@Builder
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ApiResponse register(@RequestBody final UserRegisterRequest userRegisterRequest) {
        log.info("email: {}", userRegisterRequest.getEmail());
        Long savedUserId = userService.register(userRegisterRequest);
        return ApiResponse.generate()
                .put("id", savedUserId);
    }
}
