package com.mentormentee.core.exception;

public record ErrorReason(Integer status, String message) {
    public static ErrorReason of(Integer status, String message) {
        return new ErrorReason(status, message);
    }
}
