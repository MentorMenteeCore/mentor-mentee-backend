package com.mentormentee.core.controller;

import com.mentormentee.core.dto.*;
import com.mentormentee.core.exception.ExceptionResponse;
import com.mentormentee.core.exception.exceptionCollection.UserNotFoundException;
import com.mentormentee.core.exception.exceptionCollection.UserNotMatchedException;
import com.mentormentee.core.service.UserService;
import com.mentormentee.core.token.dto.AuthToken;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    /**
     * @return UserInformDto
     */
    @GetMapping("/user/information")
    public UserInformDto getUserInformation() {
        UserInformDto userinformation = userService.getUserinformation();
        return userinformation;
    }

    /**
     * 회원가입입니다.
     * @param userSignUpRequestDto : 이름, 닉네임, 비밀번호 ..etc
     * @return id(pk)
     */
    @PostMapping("/user/sign-up")
    public ResponseEntity<Long> signUp(@RequestBody UserSignUpRequestDto userSignUpRequestDto){
        return ResponseEntity.ok(userService.save(userSignUpRequestDto));
    }

    /**
     * 로그인입니다.
     *
     * @param loginRequestDto : email, pw
     * @return auth token -> grant Type : bearer
     *                      access token : api 이용 시 사용
     *                      refresh token : access token이 만료되었을 시 사용
     */
    @PostMapping("/user/login")
    public ResponseEntity<AuthToken> login(@RequestBody LoginRequestDto loginRequestDto){
        return ResponseEntity.ok(userService.login(loginRequestDto));
    }

    /**
     * 유저가 수정을 하면 그 부분을 DB에 반영합니다.
     * @return ResponseEntity
     * @RequestBody 내용을 DTO로 반환
     */
    @PatchMapping("/user/information")
    public ResponseEntity<?> updateUserInformationController(@Valid @RequestBody UserInformDto userInformation) {
        Long updateduser = userService.updateUserInformationService(userInformation);
        if (updateduser == null) {
            throw new UserNotFoundException();
        }
        return ResponseEntity.ok(new ResponseCode(200));
    }

    /**
     * 이메일을 가지고 있는 유저가 DB에 있는지 확인
     * 그 이후 있으면 아이디 삭제 처리.
     */
    @DeleteMapping("/user")
    public ResponseEntity<?> deleteUserController(@RequestBody UserInformDto userInformation) {
        try {
            String userEmail = userInformation.getUserEmail();
            userService.deleteUserByEmail(userEmail);
        }catch (Exception e){
            throw new UserNotMatchedException();
        }
        return ResponseEntity.ok(new ResponseCode(200));
    }

    /**
     * 유저가 비밀번호를 변경하기 버튼을 누르면 실행되는 API 입니다.
     */
    @PatchMapping("/user/password")
    public ResponseEntity<?> updateUserPasswordController(@Valid @RequestBody PasswordUpdateDto passwordUpdateDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String defaultMessage = bindingResult.getFieldError().getDefaultMessage();
            ExceptionResponse exceptionResponse = new ExceptionResponse();
            exceptionResponse.setMessage(defaultMessage);
            exceptionResponse.setDetails("/user/password");
            exceptionResponse.setTimestamp(new Date());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
        }
        userService.updatePassword(passwordUpdateDto);
        return ResponseEntity.ok(new ResponseCode(200));
    }

    @PostMapping("/user/role")
    public ResponseEntity<?> updateRoleController(){
        userService.changeRole();
        return ResponseEntity.ok(new ResponseCode(200));
    }
}
