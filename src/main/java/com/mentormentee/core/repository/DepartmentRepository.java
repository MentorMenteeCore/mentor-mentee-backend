package com.mentormentee.core.repository;

import com.mentormentee.core.domain.College;
import com.mentormentee.core.domain.Department;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

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
    public List<Department> findDepartmentByCollege(String college) {
        List<Department> departmentList = em.createQuery("select d from Department d where d.college.collegeName = :college", Department.class)
                .setParameter("college", college)
                .getResultList();

        return departmentList;
    }
}
