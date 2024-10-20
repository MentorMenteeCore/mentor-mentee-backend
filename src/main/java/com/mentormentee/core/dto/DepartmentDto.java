package com.mentormentee.core.dto;

import com.mentormentee.core.domain.Department;
import lombok.Getter;

@Getter
public class DepartmentDto {
    private Long departmentId;
    private Long collegeId;
    private String departmentName;
    private String departmentImageUrl;

    // 생성자
    public DepartmentDto(Department department) {
        this.departmentId = department.getId();
        this.collegeId = department.getCollege().getId();
        this.departmentName = department.getDepartmentName();
        this.departmentImageUrl = department.getDepartmentImageUrl();
    }
}
