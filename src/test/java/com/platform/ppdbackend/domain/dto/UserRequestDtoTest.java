package com.platform.ppdbackend.domain.dto;

import com.platform.ppdbackend.domain.dto.UserRequestDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserRequestDtoTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void testValidUserRequestDto() {
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setName("JJ");
        userRequestDto.setEmail("jj@example.com");
        userRequestDto.setPassword("password");
        userRequestDto.setAge(25);

        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(userRequestDto);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testInvalidUserRequestDto() {
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setName("");
        userRequestDto.setEmail("invalid-email");
        userRequestDto.setPassword("");
        userRequestDto.setAge(-5);

        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(userRequestDto);

        assertEquals(4, violations.size()); // 위반 갯수 확인

        assertTrue(violations.stream().anyMatch(violation -> "이름은 필수 입력 값입니다.".equals(violation.getMessage())));
        assertTrue(violations.stream().anyMatch(violation -> "이메일 형식에 맞지 않습니다.".equals(violation.getMessage())));
        assertTrue(violations.stream().anyMatch(violation -> "비밀번호는 필수 입력 값입니다.".equals(violation.getMessage())));
        assertTrue(violations.stream().anyMatch(violation -> "나이는 0 이상이어야 합니다.".equals(violation.getMessage())));
    }
}
