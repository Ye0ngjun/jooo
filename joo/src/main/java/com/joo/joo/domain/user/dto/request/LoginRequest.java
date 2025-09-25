package com.joo.joo.domain.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class LoginRequest {
    @Schema(description = "사번", example = "12341234")
    private Integer empNum;

    @Schema(description = "비밀번호", example = "f1234")
    private String password;
}
