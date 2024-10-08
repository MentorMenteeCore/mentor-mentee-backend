package com.mentormentee.core.exception.exceptionCollection;

import com.mentormentee.core.exception.CustomException;
import com.mentormentee.core.exception.ErrorCode;

public class NickNameIsNotExistedException extends CustomException {

    public static final NickNameIsNotExistedException EXCEPTION = new NickNameIsNotExistedException();

    public NickNameIsNotExistedException() {
        super(ErrorCode.NICKNAME_NOT_EXISTED);
    }

}
