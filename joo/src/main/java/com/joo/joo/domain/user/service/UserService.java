package com.joo.joo.domain.user.service;

import com.joo.joo.domain.user.dto.request.CreateUserRequest;
import com.joo.joo.domain.user.dto.response.UserResponse;
import com.joo.joo.domain.user.entity.User;
import com.joo.joo.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class UserService {
    private final UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UserResponse createUser(CreateUserRequest userResponse) {
        User user = User.builder()
                .username(userResponse.getUsername())
                .email(userResponse.getEmail())
                .password(passwordEncoder.encode(userResponse.getPassword()))
                .build();
        User savedUser = userRepository.save(user);
        return convertToDTO(savedUser);
    }
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToDTO)
                .toList();
    }

    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return convertToDTO(user);
    }

    private UserResponse convertToDTO(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }
}