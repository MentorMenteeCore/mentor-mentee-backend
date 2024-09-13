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
import static com.mentormentee.core.domain.Role.ROLE_MENTOR;
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

            College college1 = new College();
            college1.createCollege(CollegeName.ENGINEERING);
            em.persist(college1);


            College college2 = new College();
            college2.createCollege(CollegeName.EDUCATION);
            em.persist(college2);


            College college3 = new College();
            college3.createCollege(CollegeName.AGRICULTURE);
            em.persist(college3);


            College college4 = new College();
            college4.createCollege(CollegeName.COMPUTERENGINEERING);
            em.persist(college4);

            Department department1 = new Department();
            department1.createDepartment("수학교육과","www.exampleImage1.com",college2);
            em.persist(department1);

            Department department2 = new Department();
            department2.createDepartment("윤리교육과","www.exampleImage2.com",college2);
            em.persist(department2);

            Department department3 = new Department();
            department3.createDepartment("국어교육과","www.exampleImage3.com",college2);
            em.persist(department3);

            Department department4 = new Department();
            department4.createDepartment("기계공학과","www.exampleImage4.com",college1);
            em.persist(department4);

            Department department5 = new Department();
            department5.createDepartment("컴공","www.exampleImage5.com",college1);
            em.persist(department5);

            Department department6 = new Department();
            department6.createDepartment("농사과","www.exampleImage6.com",college3);
            em.persist(department6);

            Department department7 = new Department();
            department7.createDepartment("정보통신공학과","www.exampleImage7.com",college1);
            em.persist(department7);

            User user1 = new User();
            user1.createUser(
                    "choegiyeon", "choegi", ROLE_MENTEE, "choegi@example.com",
                    "password123", LocalTime.of(9, 0), LocalTime.of(17, 0),
                    FACETOFACE, 2, "www.exampleProfilePicture1.com",
                    department5, "sampleRefreshToken1","최기연입니다. 충북대 학생 입니다. 안녕하세요! "
            );
            user1.hashPassword(passwordEncoder);

            em.persist(user1);

            User user2 = new User();
            user2.createUser(
                    "박상현", "나는야박상", ROLE_MENTEE, "cs1@example.com",
                    "password1234", LocalTime.of(9, 0), LocalTime.of(18, 0),
                    FACETOFACE, 2, "www.exampleProfilePicture2.com",
                    department7, "sampleRefreshToken2","선배 탕후루도 같이"
            );
            user2.hashPassword(passwordEncoder);
            em.persist(user2);

            User user3 = new User();
            user3.createUser(
                    "최기연", "어디로가야하오", ROLE_MENTOR, "cs2@example.com",
                    "password12345", LocalTime.of(9, 0), LocalTime.of(18, 0),
                    FACETOFACE, 2, "www.exampleProfilePicture3.com",
                    department7, "sampleRefreshToken3","저는 충북대 컴공을 전공중인 멘토입니다. 어서 저에게 연락을 주세요!"
            );
            user3.hashPassword(passwordEncoder);
            em.persist(user3);

            PreferredTeachingMethod preferredTeachingMethod1 = new PreferredTeachingMethod();
            preferredTeachingMethod1.createTeachingMethod("자기주도_학습_야자_싫어");
            em.persist(preferredTeachingMethod1);

            PreferredTeachingMethod preferredTeachingMethod2 = new PreferredTeachingMethod();
            preferredTeachingMethod2.createTeachingMethod("교수님과_함께하는_수업");
            em.persist(preferredTeachingMethod2);

            PreferredTeachingMethod preferredTeachingMethod3 = new PreferredTeachingMethod();
            preferredTeachingMethod3.createTeachingMethod("멘토와 줌으로");
            em.persist(preferredTeachingMethod3);

            PreferredTeachingMethod preferredTeachingMethod4 = new PreferredTeachingMethod();
            preferredTeachingMethod4.createTeachingMethod("커뮤니케이션 중요");
            em.persist(preferredTeachingMethod4);

            PreferredTeachingMethod preferredTeachingMethod5 = new PreferredTeachingMethod();
            preferredTeachingMethod5.createTeachingMethod("카톡 중요");
            em.persist(preferredTeachingMethod5);

            UserPreferredTeachingMethod userPreferredTeachingMethod1 = new UserPreferredTeachingMethod();
            userPreferredTeachingMethod1.createUserMethod(user2,preferredTeachingMethod1);
            em.persist(userPreferredTeachingMethod1);

            UserPreferredTeachingMethod userPreferredTeachingMethod2 = new UserPreferredTeachingMethod();
            userPreferredTeachingMethod2.createUserMethod(user2,preferredTeachingMethod2);
            em.persist(userPreferredTeachingMethod2);

            UserPreferredTeachingMethod userPreferredTeachingMethod3 = new UserPreferredTeachingMethod();
            userPreferredTeachingMethod3.createUserMethod(user3,preferredTeachingMethod3);
            em.persist(userPreferredTeachingMethod3);

            UserPreferredTeachingMethod userPreferredTeachingMethod4 = new UserPreferredTeachingMethod();
            userPreferredTeachingMethod4.createUserMethod(user3,preferredTeachingMethod4);
            em.persist(userPreferredTeachingMethod4);

            UserPreferredTeachingMethod userPreferredTeachingMethod5 = new UserPreferredTeachingMethod();
            userPreferredTeachingMethod5.createUserMethod(user3,preferredTeachingMethod5);
            em.persist(userPreferredTeachingMethod5);

            Course course1 = new Course();
            course1.createCourse("디지털공학", 3, "최준성", CourseYear.SOPHOMORE, department7);
            em.persist(course1);

            Course course2 = new Course();
            course2.createCourse( "공학수학1", 3, "심동규", CourseYear.SOPHOMORE, department7);
            em.persist(course2);

            Course course3 = new Course();
            course3.createCourse( "영어수업", 3, "심동규", CourseYear.SOPHOMORE, department7);
            em.persist(course3);

            Course course4 = new Course();
            course4.createCourse( "공학수학2", 3, "심동규", CourseYear.SOPHOMORE, department7);
            em.persist(course4);

            UserCourse usercourse1 = new UserCourse();
            usercourse1.createUserCourse(
                    user2, course2, GradeStatus.APLUS, IsMajor.MAJOR
            );
            em.persist(usercourse1);

            UserCourse usercourse2 = new UserCourse();
            usercourse2.createUserCourse(
                    user3, course2, GradeStatus.A, IsMajor.MAJOR
            );
            em.persist(usercourse2);

            UserCourse usercourse3 = new UserCourse();
            usercourse3.createUserCourse(
                    user1, course2, GradeStatus.B, IsMajor.MAJOR
            );
            em.persist(usercourse3);

            UserCourse usercourse4 = new UserCourse();
            usercourse4.createUserCourse(
                    user2, course1, GradeStatus.APLUS, IsMajor.MAJOR
            );
            em.persist(usercourse4);

            UserCourse usercourse5 = new UserCourse();
            usercourse5.createUserCourse(
                    user3, course3, GradeStatus.APLUS, IsMajor.MAJOR
            );
            em.persist(usercourse5);

            UserCourse usercourse6 = new UserCourse();
            usercourse6.createUserCourse(
                    user3, course4, GradeStatus.APLUS, IsMajor.MAJOR
            );
            em.persist(usercourse6);

        }
    }

}
