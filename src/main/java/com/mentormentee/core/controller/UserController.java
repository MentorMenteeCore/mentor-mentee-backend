package com.mentormentee.core.controller;

import com.mentormentee.core.domain.User;
import com.mentormentee.core.dto.*;
import com.mentormentee.core.exception.UserNotFoundException;
import com.mentormentee.core.service.UserService;
import com.mentormentee.core.token.dto.AuthToken;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("유저의 정보가 존재하지 않습니다.");
        }
        return ResponseEntity.ok(new ResponseCode(200));
    }

    /**
     * UserEmailDto로 이메일 받은 다음에 그 이메일을 서비스에 보내서 기존 DB에 있는지 확인
     * 그 이후 있으면 아이디 삭제 처리.
     */
    @DeleteMapping("/user")
    public ResponseEntity<?> deleteUserController(@RequestParam String useremail) {
        Long l = userService.deleteUserByEmail(useremail);
        if (l == null) {
            throw new UserNotFoundException("USER NOT FOUND");
        }
        return ResponseEntity.ok(new ResponseCode(200));
    }

}
