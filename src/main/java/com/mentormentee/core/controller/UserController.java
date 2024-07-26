package com.mentormentee.core.controller;

import com.mentormentee.core.domain.User;
import com.mentormentee.core.dto.ResponseCode;
import com.mentormentee.core.dto.UserEmailDto;
import com.mentormentee.core.dto.UserInformDto;
import com.mentormentee.core.exception.UserNotFoundException;
import com.mentormentee.core.service.UserService;
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
     * 유저의 id 파악하고 DTO에 필요한 유저정보 담아서 반환합니다.
     *
     * @param id 유저 아이디
     * @return UserInformDto
     */
    @GetMapping("/user/{id}/information")
    public UserInformDto getUserInformation(@PathVariable Long id) {
        UserInformDto userinformation = userService.getUserinformation(id);
        return userinformation;
    }


    /**
     * 유저가 수정을 하면 그 부분을 DB에 반영합니다.
     * @param id 유저 아이디
     * @return ResponseEntity
     * @RequestBody 내용을 DTO로 반환
     */
    @PatchMapping("/user/{id}/information")
    public ResponseEntity<?> updateUserInformationController(@PathVariable Long id, @Valid @RequestBody UserInformDto userInformation) {
        Long updateduser = userService.updateUserInformationService(id, userInformation);
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
    public ResponseEntity<?> deleteUserController(@RequestBody UserEmailDto userEmailDto) {
        Long l = userService.deleteUserByEmail(userEmailDto);
        if (l == null) {
            throw new UserNotFoundException("USER NOT FOUND");
        }
        return ResponseEntity.ok(new ResponseCode(200));
    }



}
