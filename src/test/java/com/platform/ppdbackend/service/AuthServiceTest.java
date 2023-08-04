package com.platform.ppdbackend.service;

import com.platform.ppdbackend.domain.dto.TokenDto;
import com.platform.ppdbackend.domain.dto.UserRequestDto;
import com.platform.ppdbackend.jwt.TokenProvider;
import com.platform.ppdbackend.repository.RefreshTokenRepository;
import com.platform.ppdbackend.repository.UserRepository;
import com.platform.ppdbackend.domain.user.RefreshToken;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RequiredArgsConstructor
class AuthServiceTest {

    @Autowired
    private  AuthenticationManagerBuilder authenticationManagerBuilder;
    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private  PasswordEncoder passwordEncoder;
    @Autowired
    private  TokenProvider tokenProvider;
    @Autowired
    private  RefreshTokenRepository refreshTokenRepository;

    @Test
    void login() {
        //given
        UserRequestDto userRequestDto = new UserRequestDto("jjj","2967");
        //when
        UsernamePasswordAuthenticationToken authenticationToken = userRequestDto.toAuthentication();
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        RefreshToken refreshToken = RefreshToken.builder()
                .key(authentication.getName())
                .value(tokenDto.getRefreshToken())
                .build();

        refreshTokenRepository.save(refreshToken);

        //then
        assertSame(authenticationToken.getName(),userRequestDto.getEmail());
        assertTrue(!tokenDto.getAccessToken().isEmpty());
        assertTrue(authentication.isAuthenticated());
    }
}