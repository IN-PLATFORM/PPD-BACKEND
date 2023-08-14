package com.platform.ppdbackend.service;

import com.platform.ppdbackend.domain.dto.LoginDto;
import com.platform.ppdbackend.domain.dto.TokenDto;
import com.platform.ppdbackend.domain.dto.UserRequestDto;
import com.platform.ppdbackend.domain.dto.UserResponseDto;
import com.platform.ppdbackend.domain.user.User;
import com.platform.ppdbackend.jwt.TokenProvider;
import com.platform.ppdbackend.repository.RefreshTokenRepository;
import com.platform.ppdbackend.repository.UserRepository;
import com.platform.ppdbackend.domain.user.RefreshToken;
import jakarta.transaction.Transactional;
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
    @Transactional
    void userEmailChk(){
        //given
        UserRequestDto userRequestDto = new UserRequestDto("ju","test1@rs", "test",1);
        UserResponseDto savedDto = authService.signup(userRequestDto);

        //when
        int result = authService.userEmailChk(savedDto.getEmail());

        //then
        Assertions.assertEquals(result, 1);

    }
    @Test
    @Transactional
    void signup() {
        //given
        UserRequestDto userRequestDto = new UserRequestDto("ju","test1@rs", "test",2);

        User user = userRequestDto.toUser(passwordEncoder);
        UserResponseDto userResponseDto = UserResponseDto.of(userRepository.save(user));


        //then
        assertTrue(authService.userEmailChk(userRequestDto.getEmail()) == 1);
        assertTrue(userResponseDto != null);
        assertSame(userRequestDto.getEmail(), userResponseDto.getEmail());


    }
    @Test
    @Transactional
    void login() {
        //given
        UserRequestDto userRequestDto = new UserRequestDto("ju","test1", "test",1);
        UserResponseDto savedDto = authService.signup(userRequestDto);

        LoginDto loginDto = new LoginDto(userRequestDto.getEmail(), userRequestDto.getPassword());
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