package com.mentormentee.core.exception.exceptionCollection;

import com.mentormentee.core.exception.CustomException;
import com.mentormentee.core.exception.ErrorCode;

public class JWTClaimException extends CustomException {

    public static final JWTClaimException EXCEPTION = new JWTClaimException();

    public JWTClaimException() {
        super(ErrorCode.JWT_EMAIL);
    }

}
