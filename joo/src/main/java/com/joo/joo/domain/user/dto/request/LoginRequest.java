package com.joo.joo.domain.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor
@Getter
public class LoginRequest {
    @NotNull
    @Schema(description = "사번", example = "12341234")
    private Integer empNum;

    @NotNull
    @Size(min = 4, max = 64)
    @Schema(description = "비밀번호", example = "f1234")
    private String password;
}
