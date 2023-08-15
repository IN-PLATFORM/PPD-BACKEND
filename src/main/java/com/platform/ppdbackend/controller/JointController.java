package com.platform.ppdbackend.controller;


import com.platform.ppdbackend.domain.dto.JointDto;
import com.platform.ppdbackend.domain.dto.JointRequestDto;
import com.platform.ppdbackend.domain.dto.UserInfoDto;
import com.platform.ppdbackend.error.Message;
import com.platform.ppdbackend.error.StatusEnum;
import com.platform.ppdbackend.service.JointService;
import com.platform.ppdbackend.service.UserService;
import com.platform.ppdbackend.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/joint")
public class JointController {

    private final UserService userService;
    private final JointService jointService;

    // 데이터 추가
    @PostMapping("/new")
    public ResponseEntity<Message> insertData(@RequestBody JointRequestDto jointRequestDto){
        Message message = new Message();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application","json", Charset.forName("UTF-8")));

        UserInfoDto userInfoDto  = userService.findUserInfoByHeader(SecurityUtil.getCurrentMemberId());
        if(userInfoDto != null){
            JointDto jointDto = jointService.saveNewData(userInfoDto.getEmail(), jointRequestDto);
            message.setStatus(StatusEnum.OK);
            message.setMessage("새로운 데이터를 저장했습니다.");
            message.setData(jointDto);
        }
        return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }

    // 사용자의 데이터 보기
    @GetMapping("")
    public ResponseEntity<Message> getDataList(){
        Message message = new Message();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application","json", Charset.forName("UTF-8")));

        UserInfoDto userInfoDto  = userService.findUserInfoByHeader(SecurityUtil.getCurrentMemberId());
        if(userInfoDto != null){
            List<JointDto> jointDtoList = jointService.getUserDataList(userInfoDto.getEmail());
            message.setStatus(StatusEnum.OK);
            message.setMessage("user 의 data list 를 요청했습니다.");
            message.setData(jointDtoList);
        }
        return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }

    // idx 따라 데이터 삭제
    @DeleteMapping ("/{idx}")
    public ResponseEntity<Message> deleteData(@PathVariable Long idx){

        Message message = new Message();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application","json", Charset.forName("UTF-8")));
        boolean result = jointService.deleteData(idx);

        message.setStatus(StatusEnum.OK);
        message.setMessage("데이터를 삭제했습니다.");
        message.setData(result);
        return new ResponseEntity<>(message, headers, HttpStatus.OK);

    }
}
