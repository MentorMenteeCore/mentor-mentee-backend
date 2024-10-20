package com.mentormentee.core.exception.exceptionCollection;

import com.mentormentee.core.exception.CustomException;
import com.mentormentee.core.exception.ErrorCode;

public class DuplicatedEmailException extends CustomException {

    public static final DuplicatedEmailException EXCEPTION = new DuplicatedEmailException();

    public DuplicatedEmailException() {
        super(ErrorCode.DUPLICATED_EMAIL);
    }

}
