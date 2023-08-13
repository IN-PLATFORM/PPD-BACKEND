package com.platform.ppdbackend.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice // Controller에서 에러가 발생시 처리할 수 있다.
public class GlobalExceptionHandler {
        @ExceptionHandler(Exception.class)
        public ResponseEntity<Message> handleServerErrorException(Exception ex){
            log.error("handleException",ex);
            Message message = new Message();
            message.setStatus(StatusEnum.INTERNAL_SERVER_ERROR);
            message.setMessage(ex.getMessage());
            return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
        }
}

