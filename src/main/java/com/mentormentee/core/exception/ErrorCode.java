package com.mentormentee.core.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ErrorCode implements BaseErrorCode {
    NOT_FOUND(404, "유저를 찾을 수 없습니다."),
    NOT_MATCHED(404,"잘못된 이메일입니다."),
    UNAUTHORIZED(401,"User is not authorized"),
    DUPLICATED(409, "중복 데이터입니다"),
    DUPLICATED_EMAIL(409, "중복 이메일 입니다."),
    JWT_EMAIL(400,"JWT 클레임 이메일이 DB에 존재하지 않습니다."),
    JWT_BAD_REQUEST(400, "Access Token 불일치"),
    ILLEGAL_ARGUMENT(400, "올바르지 않은 요청입니다."),
    PASSWORD_NOT_MATCHED(400, "입력한 비밀번호가 기존 비밀번호와 같지 않습니다."),
    NEW_PASSWORD_NOT_MATCHED(400, "새 비밀번호가 일치하지 않습니다."),
    ENTERED_EXISTED_PASSWORD(400,"기존 비밀번호랑 다른 비밀번호를 입력해 주세요."),
    NICKNAME_EXIST(400,"유저의 닉네임이 이미 존재합니다");

    private final Integer status;
    private final String message;

    @Override
    public ErrorReason getErrorReason() {
        return ErrorReason.of(status,message);
    }

}
