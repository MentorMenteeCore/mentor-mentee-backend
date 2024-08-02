package com.mentormentee.core.repository;

import com.mentormentee.core.domain.College;
import com.mentormentee.core.domain.CollegeName;
import com.mentormentee.core.domain.Department;
import com.mentormentee.core.dto.DepartmentDto;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class DepartmentRepository {

    private final EntityManager em;


    /**
     * 이 메서드는 단과대 이름을 파라미터로 받으면
     * 단과대 소속 학부를 리스트로 다 가져옵니다.
     *
     * @param : 학부이름 ex) 공과대학, 경영대학, 의과대학
     * @return : 학과 목록
     */
    public List<DepartmentDto> findDepartmentByCollege(CollegeName college) {
        List<Department> departmentList = em.createQuery("select d from Department d where d.college.collegeName = :college", Department.class)
                .setParameter("college", college)
                .getResultList();

        List<DepartmentDto> DtoList = departmentList.stream().map(DepartmentDto::new).collect(Collectors.toList());

        return DtoList;
    }

    /**
     * department이름 가지고 department를 찾아낸다.
     */
    public Department findDepartmentByName(String DepartmentName) {
        Department departmentName = em.createQuery("Select d from Department d where d.departmentName = :DepartmentName", Department.class)
                .setParameter("DepartmentName", DepartmentName)
                .getSingleResult();
        return departmentName;
    }

}
