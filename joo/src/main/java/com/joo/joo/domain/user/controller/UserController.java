package com.joo.joo.domain.user.controller;

import com.joo.joo.domain.user.dto.request.CreateUserRequest;
import com.joo.joo.domain.user.dto.request.LoginRequest;
import com.joo.joo.domain.user.dto.response.UserResponse;
import com.joo.joo.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Tag(name = "User", description = "유저 API")

public class UserController {
    private final UserService userService;

    @PostMapping
    @Operation(summary = "회원가입")
    public ResponseEntity<String> createUser(@Valid @RequestBody CreateUserRequest request) {
        String message = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    @PostMapping("/login")
    @Operation(summary = "로그인")
    public ResponseEntity<String> login(
            @Valid @RequestBody LoginRequest request,
            HttpServletResponse response
    ) {
        return ResponseEntity.ok(userService.login(request, response));
    }

    @PostMapping("/logout")
    @Operation(summary = "로그아웃")
    public ResponseEntity<String> logout(HttpServletResponse response) {
        String message = userService.logout(response);
        return ResponseEntity.ok(message);
    }


    @PostMapping("/all")
    @Operation(summary = "모든 유저 조회")
    public List<UserResponse> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/me")
    @Operation(summary = "내 정보 조회")
    public UserResponse getUserByUserId(HttpServletRequest request) {
        return userService.getUserByUserId(request);
    }
}
