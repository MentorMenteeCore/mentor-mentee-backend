package com.mentormentee.core.controller;

import com.mentormentee.core.dto.CourseMentorDto;
import com.mentormentee.core.dto.MentorDetailsUpdateDto;
import com.mentormentee.core.dto.ResponseCode;
import com.mentormentee.core.service.CourseMentorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CourseMentorController {

    private final CourseMentorService courseMentorService;

    @GetMapping("/courseMentors/{departmentId}")
    public CourseMentorDto getCourseMentorDetails(
            @PathVariable Long departmentId,
            @RequestParam(required = false) String selectedYear,
            @RequestParam(required = false) Long courseId,
            @RequestParam(required = false, defaultValue = "nickname") String sortBy,
            @RequestParam(defaultValue = "0") int page, // 페이지 번호
            @RequestParam(defaultValue = "5") int size) { // 페이지 크기(한번에 5명의 멘토를 보여준다)

        Pageable pageable = PageRequest.of(page, size);
        return courseMentorService.getCourseMentorDetails(departmentId, selectedYear, courseId, sortBy, pageable);
    }

    @GetMapping("/mentordetails/update")
    public MentorDetailsUpdateDto getMentorDetails(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return courseMentorService.getMentorDetails(pageable);
    }


    @PatchMapping("/mentordetails/update")
    public ResponseEntity<?> updateMentorDetails(
            @RequestBody MentorDetailsUpdateDto updateDto,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size) {

        // Pageable 객체 생성
        Pageable pageable = PageRequest.of(page, size);

        // 입력 데이터 검증
        String validationMessage = courseMentorService.validateMentorDetails(updateDto);
        if (validationMessage != null) {
            // 응답 메시지를 포함한 형식으로 ResponseEntity 반환
            return ResponseEntity.badRequest().body(
                    Map.of("code", 400, "message", validationMessage)
            );
        }

        // 멘토 정보 업데이트
        courseMentorService.updateMentorDetails(updateDto, pageable);

        return ResponseEntity.ok(new ResponseCode(200));
    }

}












