package com.platform.ppdbackend.controller;

import com.platform.ppdbackend.domain.dto.UserInfoDto;
import com.platform.ppdbackend.domain.dto.UserUpdateDto;
import com.platform.ppdbackend.error.Message;
import com.platform.ppdbackend.error.StatusEnum;
import com.platform.ppdbackend.service.UserService;
import com.platform.ppdbackend.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.Charset;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<Message> findMemberInfoById() {

        Message message = new Message();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application","json", Charset.forName("UTF-8")));
        UserInfoDto userInfoDto  = userService.findUserInfoByHeader(SecurityUtil.getCurrentMemberId());
        if(userInfoDto != null){
            message.setStatus(StatusEnum.OK);
            message.setMessage("사용자 정보를 조회했습니다.");
            message.setData(userInfoDto);
        }
        return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }

    @PutMapping("/me/update")
    public ResponseEntity<Message> updateUserInfo(@RequestBody UserUpdateDto userUpdateDto) {
        Message message = new Message();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        String email = SecurityUtil.getCurrentMemberId();
        UserInfoDto updatedInfo = userService.updateUser(email, userUpdateDto);

        if (updatedInfo != null) {
            message.setStatus(StatusEnum.OK);
            message.setMessage("사용자 정보를 성공적으로 수정했습니다.");
            message.setData(updatedInfo);
        }

        return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }

}
