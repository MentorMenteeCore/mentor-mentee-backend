package com.mentormentee.core.controller;

import com.mentormentee.core.domain.User;
import com.mentormentee.core.dto.*;
import com.mentormentee.core.exception.ExceptionResponse;
import com.mentormentee.core.exception.UserNotFoundException;
import com.mentormentee.core.service.S3Uploader;
import com.mentormentee.core.service.UserService;
import com.mentormentee.core.token.dto.AuthToken;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
     *
     * @param userSignUpRequestDto : 이름, 닉네임, 비밀번호 ..etc
     * @return id(pk)
     */
    @PostMapping("/user/sign-up")
    public ResponseEntity<Long> signUp(@RequestBody UserSignUpRequestDto userSignUpRequestDto) {
        return ResponseEntity.ok(userService.save(userSignUpRequestDto));
    }

    /**
     * 로그인입니다.
     *
     * @param loginRequestDto : email, pw
     * @return auth token -> grant Type : bearer
     * access token : api 이용 시 사용
     * refresh token : access token이 만료되었을 시 사용
     */
    @PostMapping("/user/login")
    public ResponseEntity<AuthToken> login(@RequestBody LoginRequestDto loginRequestDto) {
        return ResponseEntity.ok(userService.login(loginRequestDto));
    }

    /**
     * 유저가 수정을 하면 그 부분을 DB에 반영합니다.
     *
     * @return ResponseEntity
     * @RequestBody 내용을 DTO로 반환
     */
    @PatchMapping(value = "/user/information", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> updateUserInformationController(
            @RequestPart(value = "profileImage", required = false) MultipartFile profileImage,
            @RequestPart(value = "userInfo") @Valid UserInformDto userInformation) throws IOException {

        // 프로필 이미지가 있다면 업로드 처리
        if (profileImage != null && !profileImage.isEmpty()) {
            String profileUrl = userService.uploadProfileImage(profileImage); // URL을 profileUrl로 설정
            userInformation.setProfileUrl(profileUrl); // 새 이미지 URL 설정
        }

        // 유저 정보 업데이트
        Long updatedUser = userService.updateUserInformationService(userInformation);

        if (updatedUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("유저의 정보가 존재하지 않습니다.");
        }

        return ResponseEntity.ok(new ResponseCode(200));
    }


    /**
     * UserEmailDto로 이메일 받은 다음에 그 이메일을 서비스에 보내서 기존 DB에 있는지 확인
     * 그 이후 있으면 아이디 삭제 처리.
     */
    @DeleteMapping("/user")
    public ResponseEntity<?> deleteUserController() {
        Long l = userService.deleteUserByEmail();
        if (l == null) {
            throw new UserNotFoundException("USER NOT FOUND");
        }
        return ResponseEntity.ok(new ResponseCode(200));
    }

    // 프로필 이미지 삭제 API
    @DeleteMapping("/user/profile/image")
    public ResponseEntity<String> deleteProfileImage() {
        try {
            String message = userService.deleteProfileImage();
            if (message.equals("기본 프로필 이미지는 삭제할 수 없습니다.")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
            }
            return ResponseEntity.ok(message);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
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


    /**
     * 멘티가 자기 정보를 보고싶을때
     * 실행되는 API입니다.
     */


    @GetMapping("/user/mentee")
    public MenteeInformationDto getMenteeController(@RequestParam(defaultValue = "0", name = "coursePage") int coursePage,
                                                    @RequestParam(defaultValue = "0", name = "courseSize") int courseSize) {
        Pageable coursePageable = PageRequest.of(coursePage, courseSize);

        MenteeInformationDto menteeInformation = userService.getMenteeInformation(coursePageable);

        return menteeInformation;
    }

}