package com.platform.ppdbackend.domain.user.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RoleType {

    // 스프링 시큐리티에서는 관련 코드에 항상 ROLE_ 필요
    // 나중에 스프링 시큐리티 사용 대비
    NORMAL("ROLE_NORMAL", "일반회원"),
    ADMIN("ROLE_ADMIN", "관리자");
    private final String key;
    private final String title;
}
