package com.joo.joo.domain.user.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "회원가입 DTO")

public class CreateUserRequest {
    @Schema(description = "이름", example = "홍길동")
    private String username;
    @Schema(description = "이메일", example = "finger@gmail.com")
    private String email;
    @Schema(description = "비밀번호", example = "f1234")
    private String password;
}
