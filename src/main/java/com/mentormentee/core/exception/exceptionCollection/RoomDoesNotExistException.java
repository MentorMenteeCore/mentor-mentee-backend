package com.mentormentee.core.exception.exceptionCollection;

import com.mentormentee.core.exception.CustomException;
import com.mentormentee.core.exception.ErrorCode;

public class RoomDoesNotExistException extends CustomException {

    public static final RoomDoesNotExistException EXCEPTION = new RoomDoesNotExistException();

    public RoomDoesNotExistException() {
        super(ErrorCode.ROOM_DOES_NOT_EXIST);
    }

}
