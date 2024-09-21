package com.mentormentee.core.controller;

import com.mentormentee.core.dto.MenteeInformationDto;
import com.mentormentee.core.dto.ResponseCode;
import com.mentormentee.core.dto.UpdateMenteeInformDto;
import com.mentormentee.core.service.MenteeService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MenteeController {

    private final MenteeService menteeService;
    /**
     * 멘티가 자기 정보를 보고싶을때
     * 실행되는 API입니다.
     */

    @GetMapping("/user/mentee")
    public MenteeInformationDto getMenteeController(@RequestParam(defaultValue = "0", name = "coursePage") int coursePage,
                                                    @RequestParam(defaultValue = "0", name = "courseSize") int courseSize) {
        Pageable coursePageable = PageRequest.of(coursePage, courseSize);

        MenteeInformationDto menteeInformation = menteeService.getMenteeInformation(coursePageable);

        return menteeInformation;
    }

    @PatchMapping("/user/mentee")
    public ResponseEntity<?> updateMenteeController(@RequestBody UpdateMenteeInformDto menteeInformationDto) {

        menteeService.updateMenteeInformation(menteeInformationDto);

        return ResponseEntity.ok(new ResponseCode(200));
    }





}
