package com.mentormentee.core.exception.exceptionCollection;

import com.mentormentee.core.exception.CustomException;
import com.mentormentee.core.exception.ErrorCode;

public class UserNotFoundException extends CustomException {

    public static final UserNotFoundException EXCEPTION = new UserNotFoundException();

    public UserNotFoundException() {
        super(ErrorCode.NOT_FOUND);
    }
}