package com.mentormentee.core.exception.exceptionCollection;

import com.mentormentee.core.exception.CustomException;
import com.mentormentee.core.exception.ErrorCode;

public class NewAndOldPasswordNotMatchedException extends CustomException {

    public static final NewAndOldPasswordNotMatchedException EXCEPTION = new NewAndOldPasswordNotMatchedException();

    public NewAndOldPasswordNotMatchedException() {
        super(ErrorCode.PASSWORD_NOT_MATCHED);
    }

}
