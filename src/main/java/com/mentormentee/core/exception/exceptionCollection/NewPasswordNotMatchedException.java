package com.mentormentee.core.exception.exceptionCollection;

import com.mentormentee.core.exception.CustomException;
import com.mentormentee.core.exception.ErrorCode;

public class NewPasswordNotMatchedException extends CustomException {

    public static final NewPasswordNotMatchedException EXCEPTION = new NewPasswordNotMatchedException();

    public NewPasswordNotMatchedException() {
        super(ErrorCode.NEW_PASSWORD_NOT_MATCHED);
    }

}
