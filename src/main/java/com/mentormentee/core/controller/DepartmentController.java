package com.mentormentee.core.controller;

<<<<<<< HEAD
=======
import com.mentormentee.core.domain.CollegeName;
>>>>>>> origin/rapgodd-login-joining-verifying-with-email
import com.mentormentee.core.dto.DepartmentDto;
import com.mentormentee.core.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class DepartmentController {


    /**
     * 대상: 모든 사람(로그인 할 필요 없음.)
     *
     * 유저가 시작 페이지에서 단과대를 선택하면
<<<<<<< HEAD
     * 단과대가 URL에 가변경로로 들어간다[=.
=======
     * 단과대가 URL에 가변경로로 들어간다.
>>>>>>> origin/rapgodd-login-joining-verifying-with-email
     * 컨드롤러는 @PathVariable을 통해 그 값을 가져와서
     * DB에 보내 학과 리스트를 가져온다.
     */
    private final DepartmentService departmentService;

<<<<<<< HEAD
    @GetMapping("/{College}")
    public List<DepartmentDto> CollegeAndDepartments(@PathVariable String College) {
        List<DepartmentDto> departmentsOfCollege = departmentService.findDepartmentsByCollege(College);

        return departmentsOfCollege;
    }


=======
    @GetMapping("/college/{college}")
    public List<DepartmentDto> CollegeAndDepartments(@PathVariable CollegeName college) {
            List<DepartmentDto> departmentsOfCollege = departmentService.findDepartmentsByCollege(college);
            return departmentsOfCollege;
    }





>>>>>>> origin/rapgodd-login-joining-verifying-with-email
}
