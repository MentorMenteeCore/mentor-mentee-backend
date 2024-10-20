package com.mentormentee.core.exception.exceptionCollection;

import com.mentormentee.core.exception.CustomException;
import com.mentormentee.core.exception.ErrorCode;

public class IllegalArgumentException extends CustomException {

    public static final IllegalArgumentException EXCEPTION = new IllegalArgumentException();

    public IllegalArgumentException() {
        super(ErrorCode.ILLEGAL_ARGUMENT);
    }
}
