package com.mentormentee.core.exception.exceptionCollection;

import com.mentormentee.core.exception.CustomException;
import com.mentormentee.core.exception.ErrorCode;

public class EnteredExistedPasswordException extends CustomException {

    public static final EnteredExistedPasswordException EXCEPTION = new EnteredExistedPasswordException();

    public EnteredExistedPasswordException() {
        super(ErrorCode.ENTERED_EXISTED_PASSWORD);
    }

}
