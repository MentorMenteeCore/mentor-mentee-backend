package com.mentormentee.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * ResponseStatus 를 통해 500 Error -> 404 오류로 바꿔줌
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String userNotFoundWithId) {
        super(userNotFoundWithId);

        //super을 사용해서 부모 클래스에 예외를 던진다.
    }
}
