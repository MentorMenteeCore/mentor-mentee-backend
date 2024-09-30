package com.mentormentee.core.controller;

import com.mentormentee.core.dto.EmailSendRequestDto;
import com.mentormentee.core.dto.EmailSendResponseDto;
import com.mentormentee.core.dto.EmailVerifyRequestDto;
import com.mentormentee.core.service.EmailService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "이메일 관련 api")
public class EmailController {
    private final EmailService emailService;

    @PostMapping("/email/send")
    private ResponseEntity<EmailSendResponseDto> sendEmail(@RequestBody EmailSendRequestDto emailSendRequestDto){
        String email = emailSendRequestDto.getEmail();
        return ResponseEntity.ok(emailService.sendEmail(email));
    }
    @PostMapping("/email/verify")
    private ResponseEntity<Boolean> verifyCode(@RequestBody EmailVerifyRequestDto emailVerifyRequestDto){
        String code = emailVerifyRequestDto.getCode();
        String email = emailVerifyRequestDto.getEmail();
        return ResponseEntity.ok(emailService.verifiedCode(code,email));
    }
}
