package com.mentormentee.core.service;

import com.mentormentee.core.domain.College;
import com.mentormentee.core.domain.Department;
import com.mentormentee.core.domain.User;
import com.mentormentee.core.repository.DepartmentRepository;
import com.mentormentee.core.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor//파이널이 있는 필드만 가지고 생성자를 호출해 준다.
public class UserService {

    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;


    /**
     * 회원 저장
     */
    @Transactional
    public Long save(User user) {
        if (isEmailDuplicated(user.getEmail())) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
        return userRepository.save(user);
    }

    /**
     * 이메일 중복 확인
     */
    public boolean isEmailDuplicated(String email) {
        Optional<User> byEmail = userRepository.findByEmail(email);
        return byEmail.isPresent();
    }
<<<<<<< HEAD
=======

    /**
     * 컨트롤러에서 단과대 이름 받아오면
     * 리포지토리에 가서 Departments 리스트 뽑아옴.
     */
    public List<Department> findDepartmentsByCollege(String college) {
        List<Department> departmentByCollege = departmentRepository.findDepartmentByCollege(college);
        return departmentByCollege;
    }

>>>>>>> 3d4cb52 (새 기능 구현)
}


