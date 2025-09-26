package com.joo.joo.domain.user.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse {
    private Long userId;
    private String name;
    private String email;
    private String phoneNum;
    private Integer empNum;
    private String birth;
    private String position;
    private Integer seedCnt;
    private Integer yearFruit;
}
