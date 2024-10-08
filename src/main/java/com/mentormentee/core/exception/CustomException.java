package com.mentormentee.core.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomException extends RuntimeException {

    protected final BaseErrorCode errorCode;

    public Integer getStatus(){
        return errorCode.getErrorReason().status();
    }

    public String getMessage(){
        return errorCode.getErrorReason().message();
    }
}
