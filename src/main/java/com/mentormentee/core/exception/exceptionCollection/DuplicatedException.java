package com.mentormentee.core.exception.exceptionCollection;

import com.mentormentee.core.exception.CustomException;
import com.mentormentee.core.exception.ErrorCode;

public class DuplicatedException extends CustomException {

    public static final DuplicatedException EXCEPTION = new DuplicatedException();

    public DuplicatedException() {
        super(ErrorCode.DUPLICATED);
    }

}
