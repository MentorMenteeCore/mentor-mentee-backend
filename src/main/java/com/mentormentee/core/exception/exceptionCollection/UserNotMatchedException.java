package com.mentormentee.core.exception.exceptionCollection;


import com.mentormentee.core.exception.CustomException;
import com.mentormentee.core.exception.ErrorCode;

public class UserNotMatchedException extends CustomException {

    public static final UserNotMatchedException EXCEPTION = new UserNotMatchedException();

    public UserNotMatchedException() {
        super(ErrorCode.NOT_MATCHED);
    }
}
