package com.mentormentee.core.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.aspectj.bridge.IMessage;

import java.util.Date;

/**
 * 에러 보낼때 trace 너무 길어서 깔삼하게
 * 뽑아서 보여주고 싶은 필드만 정의
 */
@Data //setter getter 만들어줌
@AllArgsConstructor // 필드 가지고 있는 생성자 생성
@NoArgsConstructor //기본생성자 생성
public class ExceptionResponse {

    private Date timestamp;
    private String message;
    private String details;

}
