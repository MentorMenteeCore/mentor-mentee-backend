package com.mentormentee.core.service;

import com.mentormentee.core.domain.CollegeName;
import com.mentormentee.core.domain.Department;
import com.mentormentee.core.dto.DepartmentDto;
import com.mentormentee.core.dto.SearchDto;
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
    public List<DepartmentDto> findDepartmentsByCollege(CollegeName college) {
        List<DepartmentDto> departmentByCollege = departmentRepository.findDepartmentByCollege(college);
        return departmentByCollege;
    }

    /**
     * 학과 이름을 가지고 연관된 Image URL과
     * 단과대 이름을 반환합니다.
     */
    public SearchDto findDepartmentAndCollege(String departmentName) {
        Department departmentByName = departmentRepository.findDepartmentByName(departmentName);

        CollegeName collegeName = departmentByName.getCollege().getCollegeName();
        String collegeKoreanName = collegeName.getCollegeKoreanName();
        String searchedDepartmentName = departmentByName.getDepartmentName();
        String imageUrl = departmentByName.getDepartmentImageUrl();

        SearchDto searchDto = new SearchDto(imageUrl,searchedDepartmentName,collegeKoreanName);
        return searchDto;

    }
}