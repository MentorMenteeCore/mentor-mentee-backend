package com.mentormentee.core.controller;

import com.mentormentee.core.domain.User;
import com.mentormentee.core.dto.MenteeInformationDto;
import com.mentormentee.core.dto.MentorDetailsDto;
import com.mentormentee.core.dto.MentorDetailsDtoForEditing;
import com.mentormentee.core.dto.SearchDto;
import com.mentormentee.core.service.DepartmentService;
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
    private final DepartmentService departmentService;
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

    /**
     * 학과 이름으로
     * 학과이름과 이미지 URL과 단과대 이름을 보여주는 API입니다.
     *
     * 2024-09-20 최기연
     */
    @GetMapping("/search")
    public SearchDto search(@RequestParam(name = "departmentName", defaultValue = "국제경영학과") String departmentName) {
        SearchDto departmentAndCollege = departmentService.findDepartmentAndCollege(departmentName);
        return departmentAndCollege;
    }

}
