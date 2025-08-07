package com.joo.joo.domain.user.controller;

import com.joo.joo.domain.user.dto.request.CreateUserRequest;
import com.joo.joo.domain.user.dto.response.UserResponse;
import com.joo.joo.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "User", description = "유저 API")

public class UserController {
    private final UserService userService;

    @PostMapping
    @Operation(summary = "회원가입")
    public ResponseEntity<UserResponse> createUser(@RequestBody CreateUserRequest CreateUserRequest) {
        UserResponse createdUser = userService.createUser(CreateUserRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @GetMapping
    @Operation(summary = "모든 유저 조회")
    public List<UserResponse> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    @Operation(summary = "유저 Id로 유저 조회")
    public UserResponse getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }
}
