package com.mentormentee.core.controller;

import com.mentormentee.core.dto.SearchDto;
import com.mentormentee.core.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class SearchController {

    private final DepartmentService departmentService;

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
