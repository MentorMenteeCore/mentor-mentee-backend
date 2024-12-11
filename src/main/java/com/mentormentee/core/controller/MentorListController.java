package com.mentormentee.core.controller;

import com.mentormentee.core.dto.MentorListDto;
import com.mentormentee.core.service.MentorListService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MentorListController {

    private final MentorListService mentorListService;

    @GetMapping("/mentorlist/{departmentId}")
    public ResponseEntity<MentorListDto> getMentorList(
            @PathVariable Long departmentId,
            @RequestParam(required = false) String selectedYear,
            @RequestParam(required = false) Long courseId,
            @RequestParam(required = false, defaultValue = "nickname") String sortBy,
            @PageableDefault(size = 3) Pageable pageable) {

        // 학년 선택 값이 없으면 기본값 "1"으로 설정
        String year = selectedYear != null ? selectedYear : "1";

        MentorListDto mentorListDto = mentorListService.getMentorList(departmentId, year, courseId, sortBy, pageable);
        return ResponseEntity.ok(mentorListDto);
    }
}
