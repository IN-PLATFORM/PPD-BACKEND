package com.platform.ppdbackend.domain.joint.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResultType {

    NORMAL("정상"),
    ABNORMAL("비정상");

    private final String result;
}
