package com.platform.ppdbackend.service;

import com.platform.ppdbackend.domain.dto.LoginDto;
import com.platform.ppdbackend.domain.dto.TokenDto;
import com.platform.ppdbackend.domain.dto.UserRequestDto;
import com.platform.ppdbackend.jwt.TokenProvider;
import com.platform.ppdbackend.repository.RefreshTokenRepository;
import com.platform.ppdbackend.repository.UserRepository;
import com.platform.ppdbackend.domain.user.RefreshToken;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
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
    @Autowired
    private AuthService authService;

    @Test
    void userEmailChk(){
        //given
        String email = "juju";

        //when
        int result = authService.userEmailChk(email);

        //then
        Assertions.assertEquals(result, 1);

    }
    @Test
    void login() {
        //given
        LoginDto loginDto = new LoginDto("jjj","2967");
        //when
        UsernamePasswordAuthenticationToken authenticationToken = loginDto.toAuthentication();
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        RefreshToken refreshToken = RefreshToken.builder()
                .key(authentication.getName())
                .value(tokenDto.getRefreshToken())
                .build();

        refreshTokenRepository.save(refreshToken);

        //then
        assertSame(authenticationToken.getName(),loginDto.getEmail());
        assertTrue(!tokenDto.getAccessToken().isEmpty());
        assertTrue(authentication.isAuthenticated());
    }
}