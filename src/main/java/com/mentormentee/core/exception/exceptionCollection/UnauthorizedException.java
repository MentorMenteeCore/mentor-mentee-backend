package com.mentormentee.core.exception.exceptionCollection;

import com.mentormentee.core.exception.CustomException;
import com.mentormentee.core.exception.ErrorCode;

public class UnauthorizedException extends CustomException {

    public static final UnauthorizedException EXCEPTION = new UnauthorizedException();

    public UnauthorizedException() {
        super(ErrorCode.UNAUTHORIZED);
    }
}