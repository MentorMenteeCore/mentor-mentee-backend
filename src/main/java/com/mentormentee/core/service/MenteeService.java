package com.mentormentee.core.service;

import com.mentormentee.core.domain.*;
import com.mentormentee.core.dto.*;
import com.mentormentee.core.exception.exceptionCollection.EmailNotFoundException;
import com.mentormentee.core.exception.exceptionCollection.IllegalArgumentException;
import com.mentormentee.core.exception.exceptionCollection.JWTClaimException;
import com.mentormentee.core.repository.*;
import com.mentormentee.core.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MenteeService {

    private final UserRepository userRepository;
    private final MenteeCoursesRepository menteeCoursesRepository;
    private final CourseRepository courseRepository;
    private final TeachingMethodRepository userPreferredTeachingMethodRepository;


    /**
     * 멘티가 자기 정보 보고싶으면
     * 실행되는 메서드 입니다.
     */
    public MenteeInformationDto getMenteeInformation(Pageable coursePage) {

        String userEmail = JwtUtils.getUserEmail();
        User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new JWTClaimException());
        List<MenteePreferredTeachingMethodDto> teachingMethods = new ArrayList<>();

        /**
         * 여기서 이제 이수 교과목들, 선호하는 수업방식들 가져옵니다
         */
        Page<CourseNameAndMajorOnly> coursesByUser = menteeCoursesRepository.findCoursesByUser(user, coursePage);
        List<UserPreferredTeachingMethod> userPreferredTeachingMethodByUser = userPreferredTeachingMethodRepository.findUserPreferredTeachingMethodByUser(user);
        for (UserPreferredTeachingMethod userPreferredTeachingMethod : userPreferredTeachingMethodByUser) {

            MenteePreferredTeachingMethodDto menteePreferredTeachingMethodDto = new MenteePreferredTeachingMethodDto();
            menteePreferredTeachingMethodDto.setId(userPreferredTeachingMethod.getId());
            menteePreferredTeachingMethodDto.setMenteePreferredTeachingMethod(userPreferredTeachingMethod.getPreferredTeachingMethod());

            teachingMethods.add(menteePreferredTeachingMethodDto);
        }

        /**
         * 추출
         */
        int totalPages = coursesByUser.getTotalPages();
        int number = coursesByUser.getNumber();
        boolean last = coursesByUser.isLast();

        /**
         * 이제 Page에서 List로 변환
         */
        List<CourseNameAndMajorOnly> courseList = coursesByUser.getContent();

        /**
         * DTO로 변환
         */
        MenteeInformationDto menteeInformationDto
                = new MenteeInformationDto(totalPages, number, last, user.getNickName(), user.getUserProfilePicture(), user.getSelfIntroduction(), courseList, teachingMethods);

        /**
         * 이 Dto 컨트롤러로 보내서 API로 보내도록 하겠습니다
         */
        return menteeInformationDto;
    }


    @Transactional
    public void updateMenteeInformation(UpdateMenteeInformDto updateMenteeInformationDto) {

        String userEmail = JwtUtils.getUserEmail();
        User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new JWTClaimException());



        //수정사항이 있는 경우 -> null인경우 다 없애고 null이 아닌경우 다없애고 그걸 추가한다.
        if (updateMenteeInformationDto.getMenteePreferredTeachingMethodDtoList() != null
                && updateMenteeInformationDto.getMenteePreferredTeachingMethodDtoList().isEmpty()){
            userPreferredTeachingMethodRepository.deletePreferredTeachingMethodsByUserId(user.getId());
        } else if (updateMenteeInformationDto.getMenteePreferredTeachingMethodDtoList() != null
                && !updateMenteeInformationDto.getMenteePreferredTeachingMethodDtoList().isEmpty()) {
            //수정사함 있고 안에 수정내용 있는경우 다 없애고 그걸 추가한다.
            userPreferredTeachingMethodRepository.deletePreferredTeachingMethodsByUserId(user.getId());
            updateTeachingMethod(user, updateMenteeInformationDto.getMenteePreferredTeachingMethodDtoList());
        }

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
        if (updateMenteeInformationDto.getUserCourseList() != null) {
            updateUserCourses(user, updateMenteeInformationDto.getUserCourseList());
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
        if (newCoursesDto != null) {
            for (CourseNameDto courseDto : newCoursesDto) {
                UserCourse userCourse = new UserCourse();
                userCourse.connectUserAndUsercourse(user);
                Course course = courseRepository.findByCourseName(courseDto.getCourseName())
                        .orElseThrow(() -> IllegalArgumentException.EXCEPTION);
                userCourse.setCourse(course);
                userCourse.setGradeStatus(GradeStatus.valueOf(courseDto.getGrade()));
                userCourse.setIsMajor(IsMajor.MAJOR);

                userCourses.add(userCourse);
            }
        }

        menteeCoursesRepository.saveAll(userCourses);

    }

    /**
     * 메소드 역할
     * 요청에 유저의 선호 수업 방식 리스트가 있으면
     * 그거를 하나씩 가져와서
     * UserPreferredTeachingMethod 엔티티로 바꾸고
     * 존재하는지 검색한다.
     *
     * 존재한다면 그냥 넘기고
     * 존재하지 않는다면 UserPreferredTeachingMethod를 save한다.
     *
     * @param user
     * @param preferredTeachingMethods
     *
     * 2024-09-23 최기연
     */
    public void updateTeachingMethod(User user, List<PreferredTeachingMethodDto> preferredTeachingMethods) {

        List<UserPreferredTeachingMethod> userPreferredTeachingMethods = new ArrayList<>();

        /**
         * UserPreferredTeachingMethod 엔티티로 바꾸고 리스트에 넣는다.
         */
        for (PreferredTeachingMethodDto preferredTeachingMethod : preferredTeachingMethods) {
            UserPreferredTeachingMethod userPreferredTeachingMethod = new UserPreferredTeachingMethod();
            userPreferredTeachingMethod.createUserMethod(user, preferredTeachingMethod.getMenteePreferredTeaching());
            userPreferredTeachingMethods.add(userPreferredTeachingMethod);
        }

        /**
          객체를 Save한다.
         */
        userPreferredTeachingMethodRepository.saveAll(userPreferredTeachingMethods);

    }

    /**
     * 유저가
     */
    public MenteeInformationDto getMenteeInformationByNickname(Pageable coursePage, User user) {

        List<MenteePreferredTeachingMethodDto> teachingMethods = new ArrayList<>();

        /**
         * 여기서 이제 이수 교과목들, 선호하는 수업방식들 가져옵니다
         */
        Page<CourseNameAndMajorOnly> coursesByUser = menteeCoursesRepository.findCoursesByUser(user, coursePage);
        List<UserPreferredTeachingMethod> userPreferredTeachingMethodByUser = userPreferredTeachingMethodRepository.findUserPreferredTeachingMethodByUser(user);
        for (UserPreferredTeachingMethod userPreferredTeachingMethod : userPreferredTeachingMethodByUser) {

            MenteePreferredTeachingMethodDto menteePreferredTeachingMethodDto = new MenteePreferredTeachingMethodDto();
            menteePreferredTeachingMethodDto.setId(userPreferredTeachingMethod.getId());
            menteePreferredTeachingMethodDto.setMenteePreferredTeachingMethod(userPreferredTeachingMethod.getPreferredTeachingMethod());

            teachingMethods.add(menteePreferredTeachingMethodDto);;
        }

        /**
         * 추출
         */
        int totalPages = coursesByUser.getTotalPages();
        int number = coursesByUser.getNumber();
        boolean last = coursesByUser.isLast();

        /**
         * 이제 Page에서 List로 변환
         */
        List<CourseNameAndMajorOnly> courseList = coursesByUser.getContent();

        /**
         * DTO로 변환
         */
        MenteeInformationDto menteeInformationDto
                = new MenteeInformationDto(totalPages, number, last, user.getNickName(), user.getUserProfilePicture(), user.getSelfIntroduction(), courseList, teachingMethods);

        /**
         * 이 Dto 컨트롤러로 보내서 API로 보내도록 하겠습니다
         */
        return menteeInformationDto;

    }

}
