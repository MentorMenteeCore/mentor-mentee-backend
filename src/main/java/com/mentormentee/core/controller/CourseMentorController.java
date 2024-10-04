package com.mentormentee.core.controller;

import com.mentormentee.core.dto.CourseMentorDto;
import com.mentormentee.core.service.CourseMentorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CourseMentorController {

    private final CourseMentorService courseMentorService;

    @GetMapping("/coursementors/{departmentId}")
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
}












