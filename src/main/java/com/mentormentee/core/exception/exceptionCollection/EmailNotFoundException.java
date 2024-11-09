package com.mentormentee.core.exception.exceptionCollection;

import com.mentormentee.core.exception.BaseErrorCode;
import com.mentormentee.core.exception.CustomException;
import com.mentormentee.core.exception.ErrorCode;

public class EmailNotFoundException extends CustomException {

    public static final EmailNotFoundException EXCEPTION = new EmailNotFoundException();

    public EmailNotFoundException() {
        super(ErrorCode.EMAIL_NOT_FOUND);
    }
}
