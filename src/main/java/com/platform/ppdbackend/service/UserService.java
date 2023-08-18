package com.platform.ppdbackend.service;

import com.platform.ppdbackend.domain.dto.UserInfoDto;
import com.platform.ppdbackend.domain.dto.UserResponseDto;
import com.platform.ppdbackend.domain.dto.UserUpdateDto;
import com.platform.ppdbackend.domain.user.User;
import com.platform.ppdbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;

    public UserInfoDto findUserInfoByHeader(String email) {
        return userRepository.findByEmail(email)
                .map(UserInfoDto::of)
                .orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다."));
    }

    public UserResponseDto findUserInfoByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(UserResponseDto::of)
                .orElseThrow(() -> new RuntimeException("유저 정보가 없습니다."));
    }

    @Transactional
    public UserInfoDto updateUser(String email, UserUpdateDto userUpdateDto) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        user.update(userUpdateDto);
        // 사용자 정보를 업데이트한 후에 UserInfoDto를 반환합니다.
        return findUserInfoByHeader(email);
    }

}
