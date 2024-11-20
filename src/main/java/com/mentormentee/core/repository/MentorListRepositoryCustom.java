package com.mentormentee.core.repository;

import com.mentormentee.core.domain.Course;
import com.mentormentee.core.domain.CourseYear;
import com.mentormentee.core.dto.MentorListDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MentorListRepositoryCustom {
    Course findCourseById(Long courseId);
    Page<MentorListDto.MentorDto> findMentors(Long courseId, String sortBy, Pageable pageable);
    List<Course> findCoursesByDepartmentAndYear(Long departmentId, CourseYear courseYear);
}


