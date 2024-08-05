package com.mentormentee.core.service;

<<<<<<< HEAD
=======
import com.mentormentee.core.domain.CollegeName;
>>>>>>> origin/rapgodd-login-joining-verifying-with-email
import com.mentormentee.core.domain.Department;
import com.mentormentee.core.dto.DepartmentDto;
import com.mentormentee.core.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor//파이널이 있는 필드만 가지고 생성자를 호출해 준다.
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    /**
     * 컨트롤러에서 단과대 이름 받아오면
     * 리포지토리에 가서 Departments 리스트 뽑아옴.
     */
<<<<<<< HEAD
    public List<DepartmentDto> findDepartmentsByCollege(String college) {
=======
    public List<DepartmentDto> findDepartmentsByCollege(CollegeName college) {
>>>>>>> origin/rapgodd-login-joining-verifying-with-email
        List<DepartmentDto> departmentByCollege = departmentRepository.findDepartmentByCollege(college);
        return departmentByCollege;
    }

}
