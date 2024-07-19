package com.mentormentee.core.controller;

import com.mentormentee.core.domain.Department;
import com.mentormentee.core.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {


    /**
     * 유저가 시작 페이지에서 단과대를 선택하면
     * 단과대가 URL에 가변경로로 들어간다.
     * 컨드롤러는 @PathVariable을 통해 그 값을 가져와서
     * 서비스에 보내 학과 리스트를 가져온다.
     */

    private final UserService userService;

    @GetMapping("/{College}")
    public String CollegeAndDepartments(Model model, @PathVariable String College) {
        List<Department> departmentsByCollege = userService.findDepartmentsByCollege(College);
        model.addAttribute("departments", departmentsByCollege);

        return "user/collegeAndDepartments";
    }
}
