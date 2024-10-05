package com.mentormentee.core.exception.exceptionCollection;

import com.mentormentee.core.exception.CustomException;
import com.mentormentee.core.exception.ErrorCode;

public class LogoutUserException extends CustomException {

    public static final LogoutUserException EXCEPTION = new LogoutUserException();

    public LogoutUserException() {
        super(ErrorCode.LOGOUT_USER);
    }

}
