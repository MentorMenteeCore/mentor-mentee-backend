package com.mentormentee.core.controller;

import com.mentormentee.core.dto.MentorDetailsDto;
import com.mentormentee.core.service.MentorDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MentorDetailsController {

    private final MentorDetailsService mentorDetailsService;

    @GetMapping("/mentordetails")
    public MentorDetailsDto getMentorDetails(@RequestParam String nickName,
                                             @RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "2") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return mentorDetailsService.getMentorDetails(nickName, pageable);
    }
}



