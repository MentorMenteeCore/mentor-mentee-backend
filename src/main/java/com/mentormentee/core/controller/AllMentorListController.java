package com.mentormentee.core.controller;

import com.mentormentee.core.dto.AllMentorListDto;
import com.mentormentee.core.service.AllMentorListService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AllMentorListController {

    private final AllMentorListService allMentorListService;

    // 멘토 목록을 조회하는 GET 요청
    @GetMapping("/allmentorlist")
    public ResponseEntity<AllMentorListDto> getAllMentorList(
            @RequestParam(defaultValue = "nickname") String sortBy,
            @PageableDefault(size = 3) Pageable pageable) {

        // 서비스에서 멘토 목록을 가져옴
        AllMentorListDto allMentorListDto = allMentorListService.getAllMentorList(sortBy, pageable);

        // 응답으로 반환
        return ResponseEntity.ok(allMentorListDto);
    }
}

