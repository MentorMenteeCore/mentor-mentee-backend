package com.mentormentee.core.repository;

import com.mentormentee.core.dto.AllMentorListDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AllMentorListRepositoryCustom {
    Page<AllMentorListDto> findAllMentors(String sortBy, Pageable pageable);
}
