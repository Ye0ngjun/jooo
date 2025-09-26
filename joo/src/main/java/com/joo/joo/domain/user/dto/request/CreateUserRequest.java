package com.joo.joo.domain.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "회원가입 DTO")
public class CreateUserRequest {

    @Schema(description = "이름", example = "홍길동")
    private String name;

    @Schema(description = "이메일", example = "finger@gmail.com")
    private String email;

    @Schema(description = "비밀번호", example = "f1234")
    private String password;

    @NotBlank(message = "사번은 필수 입력 값입니다.")
    @Schema(description = "사번", example = "01234567")
    private Integer empNum;

    @Schema(description = "전화번호", example = "01012341234")
    private String phoneNum;

    @Schema(description = "생년월일", example = "1990-01-01")
    private String birth;


}
