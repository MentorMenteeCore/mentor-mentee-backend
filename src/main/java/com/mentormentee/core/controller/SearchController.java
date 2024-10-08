package com.mentormentee.core.controller;

import com.mentormentee.core.domain.User;
import com.mentormentee.core.dto.MenteeInformationDto;
import com.mentormentee.core.dto.MentorDetailsDto;
import com.mentormentee.core.dto.MentorDetailsDtoForEditing;
import com.mentormentee.core.service.MenteeService;
import com.mentormentee.core.service.UserSearchByNicknameService;
import com.mentormentee.core.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.mentormentee.core.domain.Role.ROLE_MENTOR;

/**
 * 유저 닉네임을 가지고 유저 정보를 가져오는 API입니다.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class SearchController {

    private final UserSearchByNicknameService userSearchByNicknameService;
    private final MenteeService menteeService;
    private final UserService userService;
    /**
     * 닉네임 유저가 멘토인지 멘티인지 확인
     * 멘토면 멘토 정보 불러오고
     * 멘티면 멘티 정보 불러오기
     */
    @GetMapping("/search/user")
    public Object searchUser(@RequestParam(name="nickname") String nickname
            , @PageableDefault(size = 2, sort = "course.courseName"
            , direction = Sort.Direction.ASC) Pageable pageable) {

        User user = userSearchByNicknameService.findUserByNickname(nickname);

        if(user.getUserRole()==ROLE_MENTOR){
            MentorDetailsDto mentorDetailsDto
                    = userSearchByNicknameService.getUserDetailsByUserNickname(pageable,user);
            return mentorDetailsDto;
        }else {
            MenteeInformationDto menteeInformation
                    = menteeService.getMenteeInformationByNickname(pageable,user);
            return menteeInformation;
        }

    }

}
