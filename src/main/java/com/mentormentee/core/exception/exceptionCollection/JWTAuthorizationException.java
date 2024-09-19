package com.mentormentee.core.exception.exceptionCollection;

import com.mentormentee.core.exception.CustomException;
import com.mentormentee.core.exception.ErrorCode;

public class JWTAuthorizationException extends CustomException {

    public static final JWTAuthorizationException EXCEPTION = new JWTAuthorizationException();

    public JWTAuthorizationException() {
        super(ErrorCode.JWT_BAD_REQUEST);
    }

}
