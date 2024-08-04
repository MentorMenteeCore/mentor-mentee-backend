package com.mentormentee.core;

import com.mentormentee.core.domain.*;
import com.mentormentee.core.service.UserService;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;

import static com.mentormentee.core.domain.Role.ROLE_MENTEE;
import static com.mentormentee.core.domain.WaysOfCommunication.FACETOFACE;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;


    @PostConstruct
    public void init() {
        initService.dbInit1();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService{
        private final EntityManager em;
        private final PasswordEncoder passwordEncoder;

        public void dbInit1() {

            College college1 = createCollege(CollegeName.ENGINEERING);
            em.persist(college1);

            College college2 = createCollege(CollegeName.EDUCATION);
            em.persist(college2);

            College college3 = createCollege(CollegeName.AGRICULTURE);
            em.persist(college3);

            Department department1 = createDepartment("수학교육과","www.exampleImage1.com", college2);
            em.persist(department1);

            Department department2 = createDepartment("윤리교육과","www.exampleImage2.com", college2);
            em.persist(department2);

            Department department3 = createDepartment("국어교육과","www.exampleImage3.com", college2);
            em.persist(department3);

            Department department4 = createDepartment("기계공학과","www.exampleImage4.com", college1);
            em.persist(department4);

            Department department5 = createDepartment("컴공","www.exampleImage5.com", college1);
            em.persist(department5);

            Department department6 = createDepartment("농사과","www.exampleImage6.com", college3);
            em.persist(department6);

            User user = createUser(
                    "choegiyeon", "choegi", ROLE_MENTEE, "choegi@example.com",
                    "password123", LocalTime.of(9, 0), LocalTime.of(17, 0),
                    FACETOFACE, 2, "www.exampleProfilePicture.com",
                    department5, "sampleRefreshToken"
            );
            em.persist(user);
        }



        private static User createUser(String userName, String nickName, Role userRole, String email, String password, LocalTime availableStartTime, LocalTime availableEndTime, WaysOfCommunication waysOfCommunication, int yearInUni, String userProfilePicture, Department department,String refreshToken) {
            User user = new User();
            user.setUserName(userName);
            user.setNickName(nickName);
            user.setUserRole(userRole);
            user.setEmail(email);
            user.setPassword(password);
            user.setAvailableStartTime(availableStartTime);
            user.setAvailableEndTime(availableEndTime);
            user.setWaysOfCommunication(waysOfCommunication);
            user.setYearInUni(yearInUni);
            user.setUserProfilePicture(userProfilePicture);
            user.setDepartment(department);
            user.setRefreshToken(refreshToken);
            return user;
        }

        private static Department createDepartment(String departmentName, String departmentImage, College college) {

            Department department = new Department();
            department.setDepartmentName(departmentName);
            department.setCollege(college);
            department.setDepartmentImageUrl(departmentImage);
            return department;
        }


        private static College createCollege(CollegeName collegeName) {
            College college1 = new College();
            college1.setCollegeName(collegeName);
            return college1;
        }
    }

}
