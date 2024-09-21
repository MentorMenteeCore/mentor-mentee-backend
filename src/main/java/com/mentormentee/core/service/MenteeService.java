package com.mentormentee.core.service;

import com.mentormentee.core.domain.*;
import com.mentormentee.core.dto.CourseNameDto;
import com.mentormentee.core.dto.MenteeInformationDto;
import com.mentormentee.core.dto.PreferredTeachingMethodDto;
import com.mentormentee.core.dto.UpdateMenteeInformDto;
import com.mentormentee.core.repository.*;
import com.mentormentee.core.utils.JwtUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MenteeService {



    private final UserRepository userRepository;
    private final PreferredTeachingMethodRepository preferredTeachingMethodRepository;
    private final MenteeCoursesRepository menteeCoursesRepository;
    private final CourseRepository courseRepository;
    private final UserPreferredTeachingMethodRepository userPreferredTeachingMethodRepository;
    @PersistenceContext
    private EntityManager entityManager;


    /**
     * 멘티가 자기 정보 보고싶으면
     * 실행되는 메서드 입니다.
     */
    public MenteeInformationDto getMenteeInformation(Pageable coursePage) {

        String userEmail = JwtUtils.getUserEmail();
        User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new IllegalArgumentException("유저 존재하지 않음"));

        /**
         * 여기서 이제 이수 교과목들, 선호하는 수업방식들 가져옵니다
         */
        Page<CourseNameAndMajorOnly> coursesByUser = menteeCoursesRepository.findCoursesByUser(user, coursePage);
        List<PreferredTeachingMethodOnly> TeachingMethod = preferredTeachingMethodRepository.findUserPreferredTeachingMethodByUser(user);

        /**
         * 그 페이지에서 좀 뽑아올거 있어서 뽑아올게요
         */
        int totalPages = coursesByUser.getTotalPages();
        int number = coursesByUser.getNumber();
        boolean last = coursesByUser.isLast();

        /**
         * 이제 Page에서 List로 바꿀게요
         */
        List<CourseNameAndMajorOnly> courseList = coursesByUser.getContent();

        /**
         * 자 이제 DTO에 담을게요
         */
        MenteeInformationDto menteeInformationDto
                = new MenteeInformationDto(totalPages,number,last,user.getNickName(),user.getUserProfilePicture(),user.getSelfIntroduction(),courseList,TeachingMethod);

        /**
         * 이 Dto 컨트롤러로 보내서 API로 보내도록 하겠습니다
         */
        return menteeInformationDto;
    }


    @Transactional
    public void updateMenteeInformation(UpdateMenteeInformDto updateMenteeInformationDto) {

        String userEmail = JwtUtils.getUserEmail();
        User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new IllegalArgumentException("유저 존재하지 않음"));

        // menteeImageUrl 업데이트
        if (updateMenteeInformationDto.getMenteeImageUrl() != null &&
                !updateMenteeInformationDto.getMenteeImageUrl().equals(user.getUserProfilePicture())) {
            user.setUserProfilePicture(updateMenteeInformationDto.getMenteeImageUrl());
        }

        // selfIntroduction 업데이트
        if (updateMenteeInformationDto.getSelfIntroduction() != null &&
                !updateMenteeInformationDto.getSelfIntroduction().equals(user.getSelfIntroduction())) {
            user.setSelfIntroduction(updateMenteeInformationDto.getSelfIntroduction());
        }

        // userCourseList 업데이트
        if (updateMenteeInformationDto.getUserCourseList() != null &&
                !updateMenteeInformationDto.getUserCourseList().equals(menteeCoursesRepository.findCourseNameDtoByUser(user))) {
            updateUserCourses(user, updateMenteeInformationDto.getUserCourseList());
        }

        // menteePreferredTeachingMethodDtoList 업데이트
        if (updateMenteeInformationDto.getMenteePreferredTeachingMethodDtoList() != null &&
                !updateMenteeInformationDto.getMenteePreferredTeachingMethodDtoList().equals(userPreferredTeachingMethodRepository.findTeachingMethodsByUser(user))) {
            updateTeachingMethod(user, updateMenteeInformationDto.getMenteePreferredTeachingMethodDtoList());
        }

//
         // menteeImageUrl 업데이트
//        if (updateMenteeInformationDto.getMenteeImageUrl() != null &&
//                !updateMenteeInformationDto.getMenteeImageUrl().equals(user.getUserProfilePicture())) {
//            user.setUserProfilePicture(updateMenteeInformationDto.getMenteeImageUrl());
//        }

//        // selfIntroduction 업데이트
//        if (updateMenteeInformationDto.getSelfIntroduction() != null &&
//                !updateMenteeInformationDto.getSelfIntroduction().equals(user.getSelfIntroduction())) {
//            user.setSelfIntroduction(updateMenteeInformationDto.getSelfIntroduction());
//        }
//
//        // userCourseList 업데이트
//        if (updateMenteeInformationDto.getUserCourseList() != null &&
//                !updateMenteeInformationDto.getUserCourseList().equals(menteeCoursesRepository.findCourseNameDtoByUser(user))) {
//            updateUserCourses(user, updateMenteeInformationDto.getUserCourseList());
//        }
//
//        // menteePreferredTeachingMethodDtoList 업데이트
//        if (updateMenteeInformationDto.getMenteePreferredTeachingMethodDtoList() != null &&
//                !updateMenteeInformationDto.getMenteePreferredTeachingMethodDtoList().equals(userPreferredTeachingMethodRepository.findTeachingMethodsByUser(user))) {
//            updateTeachingMethod(user, updateMenteeInformationDto.getMenteePreferredTeachingMethodDtoList());
//        }

    }


    public void updateUserCourses(User user, List<CourseNameDto> newCoursesDto) {
        // 기존의 UserCourse 엔티티를 모두 삭제
        menteeCoursesRepository.deleteByUser(user);

        List<UserCourse> userCourses = new ArrayList<>();

        // 새로운 UserCourse 엔티티를 추가
        for (CourseNameDto courseDto : newCoursesDto) {
            UserCourse userCourse = new UserCourse();
            userCourse.connectUserAndUsercourse(user);
            Course course = courseRepository.findByCourseName(courseDto.getCourseName())
                    .orElseGet(() -> {
                        Course newCourse = new Course();
                        newCourse.setCourseName(courseDto.getCourseName());
                        return courseRepository.save(newCourse);
                    });
            course.setCourseName(courseDto.getCourseName());
            userCourse.setCourse(course);
            userCourse.setIsMajor(courseDto.getIsMajor());


            userCourses.add(userCourse);
        }

        menteeCoursesRepository.saveAll(userCourses);

    }

    public void updateTeachingMethod(User user, List<PreferredTeachingMethodDto> preferredTeachingMethods) {

        userPreferredTeachingMethodRepository.deletePreferredTeachingMethodsByUser(user);
        List<UserPreferredTeachingMethod> userPreferredTeachingMethods = new ArrayList<>();


        for (PreferredTeachingMethodDto preferredTeachingMethod1 : preferredTeachingMethods) {

            UserPreferredTeachingMethod userPreferredTeachingMethod = new UserPreferredTeachingMethod();

            PreferredTeachingMethod menteePreferredTeachingMethod = new PreferredTeachingMethod();
            menteePreferredTeachingMethod.createMethod(preferredTeachingMethod1.getPreferredTeachingMethod());

            entityManager.persist(menteePreferredTeachingMethod);

            userPreferredTeachingMethod.createUserMethod(user , menteePreferredTeachingMethod);

            userPreferredTeachingMethods.add(userPreferredTeachingMethod);
        }

        userPreferredTeachingMethodRepository.saveAll(userPreferredTeachingMethods);

    }

}
