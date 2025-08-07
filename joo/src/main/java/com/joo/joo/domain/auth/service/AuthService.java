package com.joo.joo.domain.auth.service;

import com.joo.joo.domain.auth.dto.request.LoginRequest;
import com.joo.joo.domain.auth.dto.response.LoginResponse;
import com.joo.joo.domain.auth.jwt.JwtTokenProvider;
import com.joo.joo.domain.user.entity.User;
import com.joo.joo.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    public LoginResponse login(LoginRequest request) {
        // 이메일로 사용자 조회
        User user = (User) userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("등록되지 않은 이메일입니다."));

        // 비밀번호 확인
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        // JWT 토큰 생성
        String token = jwtTokenProvider.generateToken(user.getEmail());
        return new LoginResponse(token);
    }
}
