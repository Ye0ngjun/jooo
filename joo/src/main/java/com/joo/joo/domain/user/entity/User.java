package com.joo.joo.domain.user.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @Column(name = "user_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // bigint auto_increment라면
    private Long userId;

    @Column(name = "dept_id", nullable = true)
    private Long deptId;

//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "dept_id", nullable = false)
//    private Dept dept;

    @Column(name = "emp_num", nullable = false, unique = true, length = 8)
    private Integer empNum;

    @Column(name = "password", nullable = false, length = 225)
    private String password;

    @Column(name = "name", nullable = false, length = 30)
    private String name;

    @Column(name = "phone_num", nullable = false, length = 11)
    private String phoneNum;

    @Column(name = "email", nullable = false, length = 50)
    private String email;

    @Column(name = "birth", nullable = false)
    private LocalDate birth;

    @Enumerated(EnumType.STRING)
    @Column(name = "position", nullable = false, length = 20)
    private Position position;

    @Column(name = "seed_cnt", nullable = false)
    private Integer seedCnt;

    @Column(name = "year_fruit", nullable = false)
    private Integer yearFruit;

    // 직급 enum 정의
    public enum Position {
        INTERN, // 인턴
        PRO,    // 프로
        MANAGER, // 매니저
        MASTER,  // 마스터
        CAPTAIN, // 캡틴
        VICE_PRESIDENT, // 부회장
        PRESIDENT       // 회장
    }
    @PrePersist
    public void prePersist() {
        if (seedCnt == null) seedCnt = 0;
        if (yearFruit == null) yearFruit = 0;
        if (position == null) position = Position.INTERN; // 기본값 의미 있으면
    }
}
