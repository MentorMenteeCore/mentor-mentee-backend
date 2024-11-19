package com.mentormentee.core.service;//*

import com.mentormentee.core.dto.AllMentorListDto;
import com.mentormentee.core.repository.AllMentorListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AllMentorListService {

    private final AllMentorListRepository allMentorListRepository;

    public AllMentorListDto getAllMentorList(String sortBy, Pageable pageable) {
        // AllMentorListRepository에서 정렬과 페이징이 이미 처리된 멘토 목록 가져오기
        Page<AllMentorListDto> mentorsPage = allMentorListRepository.findAllMentors(sortBy, pageable);

        // 페이지 정보 추출
        int totalPages = mentorsPage.getTotalPages();
        int currentPageNum = mentorsPage.getNumber() + 1; // 1부터 시작하는 페이지 번호
        boolean lastPageOrNot = mentorsPage.isLast();

        // AllMentorListDto 객체 생성
        List<AllMentorListDto.MentorDto> mentorDtos = mentorsPage.getContent().stream()
                .flatMap(allMentorListDto -> allMentorListDto.getMentorDtos().stream())
                .collect(Collectors.toList());

        // 페이지 정보와 멘토 리스트를 포함한 AllMentorListDto 반환
        return new AllMentorListDto(
                mentorDtos,
                totalPages,
                currentPageNum,
                lastPageOrNot
        );
    }
}




