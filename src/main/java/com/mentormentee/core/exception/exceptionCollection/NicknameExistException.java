package com.mentormentee.core.exception.exceptionCollection;

import com.mentormentee.core.exception.CustomException;
import com.mentormentee.core.exception.ErrorCode;

public class NicknameExistException extends CustomException {

    public static final NicknameExistException EXCEPTION = new NicknameExistException();

    public NicknameExistException() {
        super(ErrorCode.NICKNAME_EXIST);
    }
}
