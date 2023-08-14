package com.platform.ppdbackend.domain.dto;

import com.platform.ppdbackend.domain.user.User;
import com.platform.ppdbackend.domain.user.enums.GenderType;
import com.platform.ppdbackend.domain.user.enums.InfoStatus;
import com.platform.ppdbackend.domain.user.enums.RoleType;
import com.platform.ppdbackend.domain.user.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoDto {
    private String email;
    private String name;
    private Integer age;
    private RoleType roleType; // 각 사용자의 권한(관리자/일반회원)을 관리할 Enum class
    private UserStatus status; // 각 사용자의 상태(정상/탈퇴) 관리할 Enum class
    private GenderType gender; // 각 사용자의 성별(여성/남성) 관리할 Enum clas
    private InfoStatus info; // 각 사용자의 개인정보이용(동의/비동의) 관리할 Enum class

    public static UserInfoDto of(User user) {
        return new UserInfoDto(
                user.getEmail(),
                user.getName(),
                user.getAge(),
                user.getRoleType(),
                user.getStatus(),
                user.getGender(),
                user.getInfo()
                );
    }
}
