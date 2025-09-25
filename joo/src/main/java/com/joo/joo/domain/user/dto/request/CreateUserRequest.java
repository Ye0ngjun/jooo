package com.joo.joo.domain.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@Table(name = "users")

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

    @Schema(description = "사번", example = "12341234")
    private Integer empNum;

    @Schema(description = "전화번호", example = "01012341234")
    private String phoneNum;

    @Schema(description = "생년월일", example = "19900101")
    private String birth;
}
