package com.platform.ppdbackend.domain.user.enums;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum InfoStatus {
    AGREE("동의"),
    DISAGREE("비동의");

    private final String info;
}
