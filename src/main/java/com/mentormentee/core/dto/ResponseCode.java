package com.mentormentee.core.dto;

/**
 * 200성공 응답 코드를 여러 컨트롤러에서 쓸 수 있도록 dto로 빼서 클래스를 정의했습니다.
 */
public class ResponseCode {

    private int code;

    public ResponseCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
