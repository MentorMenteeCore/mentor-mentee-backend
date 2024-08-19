package com.mentormentee.core.controller;

import com.mentormentee.core.dto.MentorDetailsDto;
import com.mentormentee.core.service.MentorDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MentorDetailsController {

    private final MentorDetailsService mentorDetailsService;

    @GetMapping("/mentordetails/{userId}")
    public MentorDetailsDto getMentorDetails(@PathVariable Long userId) {
        return mentorDetailsService.getMentorDetails(userId);
    }
}

