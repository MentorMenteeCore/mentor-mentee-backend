package com.mentormentee.core.service;

import com.mentormentee.core.domain.College;
import com.mentormentee.core.domain.Department;
import com.mentormentee.core.domain.User;
import com.mentormentee.core.dto.DepartmentDto;
import com.mentormentee.core.dto.UserEmailDto;
import com.mentormentee.core.dto.UserInformDto;
import com.mentormentee.core.exception.UserNotFoundException;
import com.mentormentee.core.repository.DepartmentRepository;
import com.mentormentee.core.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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


    public UserInformDto getUserinformation(Long id) {

        User user = userRepository.findById(id);
        UserInformDto userInformDto = new UserInformDto(user.getNickName(), user.getEmail(), user.getDepartment().getDepartmentName(), user.getYearInUni(), user.getUserProfilePicture());
        return userInformDto;

    }

    @Transactional
    public Long updateUserInformationService(Long id, UserInformDto userInformation) {

        User user = userRepository.findById(id);

        if (userInformation.getUserDepartment() != null) {
            Department departmentByName = departmentRepository.findDepartmentByName(userInformation.getUserDepartment());
            user.setDepartment(departmentByName);
        }
        if (userInformation.getUserNickname() != null) {
            user.setNickName(userInformation.getUserNickname());
        }
        if (userInformation.getYearInUni() != user.getYearInUni()) {
            user.setYearInUni(userInformation.getYearInUni());
        }
        if (userInformation.getUserEmail() != null) {
            user.setEmail(userInformation.getUserEmail());
        }
        if (userInformation.getUserImageUrl() != null) {
            user.setUserProfilePicture(userInformation.getUserImageUrl());
        }

        Long save = userRepository.save(user);

        return save;

    }

    /**
     * 유저의 이메일을 통해 유저를 찾고 유저가 존재하면
     * 유저를 삭제
     */
    @Transactional
    public Long deleteUserByEmail(UserEmailDto userEmailDto) {

           String email = userEmailDto.getEmail();
           Long userId = userRepository.findByUserEmail(email);
           if (userId != null) {
               userRepository.deleteUser(userId);
           }
        return userId;
    }
}


