package com.platform.ppdbackend.domain.user;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.platform.ppdbackend.domain.BaseTimeEntity;
import com.platform.ppdbackend.domain.joint.Joint;
import com.platform.ppdbackend.domain.user.enums.GenderType;
import com.platform.ppdbackend.domain.user.enums.InfoStatus;
import com.platform.ppdbackend.domain.user.enums.RoleType;
import com.platform.ppdbackend.domain.user.enums.UserStatus;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Setter;
import lombok.Getter;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table
@Setter
@Builder
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_idx")
    private Long idx;

    private String email;
    private String name;
    private String password;
    private Integer age;

    @Enumerated(EnumType.STRING)
    private RoleType roleType; // 각 사용자의 권한(관리자/일반회원)을 관리할 Enum class
    @Enumerated(EnumType.STRING)
    private UserStatus status; // 각 사용자의 상태(정상/탈퇴) 관리할 Enum class
    @Enumerated(EnumType.STRING)
    private GenderType gender; // 각 사용자의 성별(여성/남성) 관리할 Enum class
    @Enumerated(EnumType.STRING)
    private InfoStatus info; // 각 사용자의 개인정보이용(동의/비동의) 관리할 Enum class

    private LocalDateTime lastLoginDate;

    // Joint : User = N : 1 관계
    @OneToMany(mappedBy = "user")
    private List<Joint> joints = new ArrayList<>(); // joints 필드가 Joint 엔티티의 user 필드에 의해 매핑 되었다.

    @Builder
    public User(String email, String name, String password, Integer age, RoleType roleType, UserStatus status, GenderType gender, InfoStatus info, LocalDateTime lastLoginDate) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.age = age;
        this.roleType = roleType;
        this.status = status;
        this.gender = gender;
        this.info = info;
        this.lastLoginDate = lastLoginDate;
    }
}

