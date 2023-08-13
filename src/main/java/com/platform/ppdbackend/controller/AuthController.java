package com.platform.ppdbackend.controller;

import com.platform.ppdbackend.domain.dto.*;
import com.platform.ppdbackend.error.Message;
import com.platform.ppdbackend.error.StatusEnum;
import com.platform.ppdbackend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.Charset;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<Message> signup(@RequestBody UserRequestDto userRequestDto) {
        Message message = new Message();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application","json", Charset.forName("UTF-8")));
        // 중복 회원 확인
        if(authService.userEmailChk(userRequestDto.getEmail()) != 0){
            message.setStatus(StatusEnum.USER_DUPLICATED);
            message.setMessage("이미 존재하는 사용자 입니다.");
            return new ResponseEntity<>(message, headers, HttpStatus.CONFLICT);
        }
        UserResponseDto userResponseDto = authService.signup(userRequestDto);
        if (userResponseDto != null) {
            message.setStatus(StatusEnum.OK);
            message.setMessage("회원가입이 완료되었습니다.");
            message.setData(userResponseDto);
        }
        return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<Message> login(@RequestBody LoginDto loginDto) {
        Message message = new Message();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application","json", Charset.forName("UTF-8")));
        TokenDto tokenDto = authService.login(loginDto);
        if(tokenDto != null){
            message.setStatus(StatusEnum.OK);
            message.setMessage("로그인이 완료되었습니다.");
            message.setData(tokenDto);
        }
        return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }

    @PostMapping("/reissue")
    public ResponseEntity<TokenDto> reissue(@RequestBody TokenRequestDto tokenRequestDto) {
        return ResponseEntity.ok(authService.reissue(tokenRequestDto));
    }
}
