package com.joo.joo.domain.user.service;

import com.joo.joo.domain.user.dto.request.CreateUserRequest;
import com.joo.joo.domain.user.dto.request.LoginRequest;
import com.joo.joo.domain.user.dto.response.UserResponse;
import com.joo.joo.domain.user.entity.User;
import com.joo.joo.domain.user.jwt.JwtTokenProvider;
import com.joo.joo.domain.user.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor

public class UserService {
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public String createUser(CreateUserRequest request) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .empNum(request.getEmpNum())
                .phoneNum(request.getPhoneNum())
                .birth(LocalDate.parse(request.getBirth(), formatter))
                .deptId(1L)
                .position(User.Position.INTERN)
                .seedCnt(0)
                .yearFruit(0)
                .build();

        userRepository.save(user);

        return "회원가입이 완료되었습니다.";
    }

    public String login(LoginRequest request, HttpServletResponse response) {
        User user = userRepository.findByEmpNum(request.getEmpNum())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "사번 또는 비밀번호가 일치하지 않습니다."));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "사번 또는 비밀번호가 일치하지 않습니다.");
        }
        // JWT 발급
        String accessToken = jwtTokenProvider.generateAccessToken(user.getEmail());
        String refreshToken = jwtTokenProvider.generateRefreshToken(user.getEmail());

        // Access Token 쿠키 저장
        ResponseCookie accessCookie = ResponseCookie.from("access_token", accessToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(60 * 15) // 15분
                .sameSite("None")
                .build();

        // Refresh Token 쿠키 저장
        ResponseCookie refreshCookie = ResponseCookie.from("refresh_token", refreshToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(60 * 60 * 24 * 7) // 7일
                .sameSite("None")
                .build();

        response.addHeader("Set-Cookie", accessCookie.toString());
        response.addHeader("Set-Cookie", refreshCookie.toString());

        return "로그인 성공";

    }

    public String logout(HttpServletResponse response) {
        // Access Token 쿠키 제거
        ResponseCookie expiredAccessCookie = ResponseCookie.from("access_token", "")
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(0) // 즉시 만료
                .sameSite("None")
                .build();

        // Refresh Token 쿠키 제거
        ResponseCookie expiredRefreshCookie = ResponseCookie.from("refresh_token", "")
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(0) // 즉시 만료
                .sameSite("None")
                .build();

        // 응답 헤더에 추가 → 쿠키 삭제됨
        response.addHeader("Set-Cookie", expiredAccessCookie.toString());
        response.addHeader("Set-Cookie", expiredRefreshCookie.toString());

        return "로그아웃 성공";
    }


    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> UserResponse.builder()
                        .userId(user.getUserId())
                        .name(user.getName())
                        .email(user.getEmail())
                        .phoneNum(user.getPhoneNum())
                        .position(user.getPosition().name())
                        .empNum(Integer.valueOf(user.getEmpNum()))
                        .birth(String.valueOf(user.getBirth()))
                        .build())
                .toList();
    }

    public UserResponse getUserByUserId(HttpServletRequest request) {
        // 1. 쿠키에서 access_token 추출
        String token = extractTokenFromCookie(request);
        if (token == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "토큰이 없습니다.");
        }

        // 2. 토큰에서 email 추출 (JwtTokenProvider 안에 getEmailFromToken 같은 메소드 만들어두면 좋아요)
        String email = jwtTokenProvider.getEmailFromToken(token);

        // 3. DB 조회
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."));

        // 4. DTO 변환
        return UserResponse.builder()
                .userId(user.getUserId())
                .name(user.getName())
                .email(user.getEmail())
                .phoneNum(user.getPhoneNum())
                .position(user.getPosition().name())
                .empNum(Integer.valueOf(user.getEmpNum()))
                .birth(String.valueOf(user.getBirth()))
                .build();
    }

    // 쿠키에서 토큰 꺼내는 유틸
    private String extractTokenFromCookie(HttpServletRequest request) {
        if (request.getCookies() == null) return null;
        for (Cookie cookie : request.getCookies()) {
            if ("access_token".equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }


}