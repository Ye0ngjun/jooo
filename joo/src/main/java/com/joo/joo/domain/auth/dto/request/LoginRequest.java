package com.joo.joo.domain.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class LoginRequest {
    @Schema(description = "이메일", example = "finger@gmail.com")
    private String email;

    @Schema(description = "비밀번호", example = "f1234")
    private String password;
}
