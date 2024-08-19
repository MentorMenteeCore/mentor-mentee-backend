package com.mentormentee.core;

import com.mentormentee.core.domain.*;
import com.mentormentee.core.service.UserService;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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

                        College college1 = createCollege(CollegeName.ENGINEERING);
                        em.persist(college1);

                        College college2 = createCollege(CollegeName.EDUCATION);
                        em.persist(college2);

                        College college3 = createCollege(CollegeName.AGRICULTURE);
                        em.persist(college3);

                        College college4 = createCollege(CollegeName.COMPUTERENGINEERING);
                        em.persist(college4);

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

                        Department department7 = createDepartment("정보통신공학과","www.exampleImage7.com", college4);
                        em.persist(department7);

                        User user1 = createUser(
                                "choegiyeon", "choegi", ROLE_MENTEE, "choegi@example.com",
                                "password123", LocalTime.of(9, 0), LocalTime.of(17, 0),
                                FACETOFACE, 2, "www.exampleProfilePicture1.com",
                                department5, "sampleRefreshToken1","최:최기연은 기:기발한 연:연구중"
                        );
                        user1.hashPassword(passwordEncoder);

                        em.persist(user1);

                        User user2 = createUser(
                                "박대팔", "그럼제가선배맘에탕탕", ROLE_MENTEE, "cs1@example.com",
                                "password1234", LocalTime.of(9, 0), LocalTime.of(18, 0),
                                FACETOFACE, 2, "www.exampleProfilePicture2.com",
                                department7, "sampleRefreshToken2","선배 탕후루도 같이"
                        );
                        user2.hashPassword(passwordEncoder);
                        em.persist(user2);

                        User user3 = createUser(
                                "최금평", "어디로가야하오", ROLE_MENTOR, "cs2@example.com",
                                "password12345", LocalTime.of(9, 0), LocalTime.of(18, 0),
                                FACETOFACE, 2, "www.exampleProfilePicture3.com",
                                department7, "sampleRefreshToken3","저는 충북대 컴공을 전공중인 멘토입니다. 어서 저에게 연락을 주세요!"
                        );
                        user3.hashPassword(passwordEncoder);
                        em.persist(user3);

                        PreferredTeachingMethod preferredTeachingMethod1  = createTeachingMethod("자기주도_학습_야자_싫어");
                        em.persist(preferredTeachingMethod1);

                        PreferredTeachingMethod preferredTeachingMethod2  = createTeachingMethod("교수님과_함께하는_수업");
                        em.persist(preferredTeachingMethod2);

                        UserPreferredTeachingMethod userTeachingMethod1 = createUserTeachingMethod(user2, preferredTeachingMethod1);
                        em.persist(userTeachingMethod1);
                        UserPreferredTeachingMethod userTeachingMethod2 = createUserTeachingMethod(user2, preferredTeachingMethod2);
                        em.persist(userTeachingMethod2);

                        Course course1 = createCourse(
                                "디지털공학", 3, "최준성", CourseYear.SOPHOMORE, department7
                        );
                        em.persist(course1);

                        Course course2 = createCourse(
                                "공학수학1", 3, "심동규", CourseYear.SOPHOMORE, department7
                        );
                        em.persist(course2);

                        UserCourse usercourse1 = createUserCourse(
                                user2, course2, GradeStatus.APLUS, IsMajor.MAJOR
                        );
                        em.persist(usercourse1);

                        UserCourse usercourse2 = createUserCourse(
                                user3, course2, GradeStatus.A, IsMajor.MAJOR
                        );
                        em.persist(usercourse2);

                        UserCourse usercourse3 = createUserCourse(
                                user1, course2, GradeStatus.B, IsMajor.MAJOR
                        );
                        em.persist(usercourse3);

                        UserCourse usercourse4 = createUserCourse(
                                user2, course1, GradeStatus.APLUS, IsMajor.MAJOR
                        );
                        em.persist(usercourse4);

                        Review review1 = createReview(3, LocalDateTime.of(2024, 8, 8, 0, 0), user2, user3, "좋았습니다");
                        em.persist(review1);



                }



                private static User createUser(String userName, String nickName, Role userRole, String email, String password, LocalTime availableStartTime, LocalTime availableEndTime, WaysOfCommunication waysOfCommunication, int yearInUni, String userProfilePicture, Department department,String refreshToken, String selfIntro) {
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
                        user.setSelfIntroduction(selfIntro);
                        return user;
                }

                private static Department createDepartment(String departmentName, String departmentImage, College college) {

                        Department department = new Department();
                        department.setDepartmentName(departmentName);
                        department.setCollege(college);
                        department.setDepartmentImageUrl(departmentImage);
                        return department;
                }

                /**
                 *테스트 하기 편하게 초기 데이터 집어넣습니다.
                 */
                private static PreferredTeachingMethod createTeachingMethod(String teachingMethod) {

                        PreferredTeachingMethod preferredTeachingMethod = new PreferredTeachingMethod();
                        preferredTeachingMethod.createMethod(teachingMethod);
                        return preferredTeachingMethod;

                }

                private static UserPreferredTeachingMethod createUserTeachingMethod(User user, PreferredTeachingMethod userTeachingMethod) {

                        UserPreferredTeachingMethod UserPreferredTeachingMethod = new UserPreferredTeachingMethod();

                        UserPreferredTeachingMethod.createUserMethod(user, userTeachingMethod);

                        return UserPreferredTeachingMethod;
                }



                private static College createCollege(CollegeName collegeName) {
                        College college1 = new College();
                        college1.setCollegeName(collegeName);
                        return college1;
                }

                private static Course createCourse(String courseName, int credit, String professor, CourseYear courseYear, Department department) {
                        Course course = new Course();
                        course.setCourseName(courseName);
                        course.setCredit(credit);
                        course.setProfessor(professor);
                        course.setCourseYear(courseYear);
                        course.setDepartment(department);
                        return course;
                }

                private static UserCourse createUserCourse(User user, Course course, GradeStatus gradeStatus, IsMajor isMajor) {
                        UserCourse userCourse = new UserCourse();
                        userCourse.setUser(user);
                        userCourse.setCourse(course);
                        userCourse.setGradeStatus(gradeStatus);
                        userCourse.setIsMajor(isMajor);
                        return userCourse;
                }

                private static Review createReview(int rating, LocalDateTime reviewDate, User reviewer, User reviewee, String comment) {
                        Review review = new Review();
                        review.setRating(rating);
                        review.setReviewDate(reviewDate);
                        review.setReviewer(reviewer);
                        review.setReviewee(reviewee);
                        review.setComment(comment);
                        return review;
                }
        }

}
