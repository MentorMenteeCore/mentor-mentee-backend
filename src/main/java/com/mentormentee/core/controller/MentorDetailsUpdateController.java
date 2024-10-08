package com.mentormentee.core.controller;

import com.mentormentee.core.dto.*;
import com.mentormentee.core.service.MentorDetailsUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MentorDetailsUpdateController {

    private final MentorDetailsUpdateService mentorDetailsUpdateService;

    @GetMapping("/mentordetails/update")
    public MentorDetailsDto getMentorDetails(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return mentorDetailsUpdateService.getMentorDetails(pageable);
    }


    @PostMapping("/mentordetails/update")
    public ResponseEntity<?> postMentorDetails(
            @RequestBody MentorDetailsUpdateDto updateDto) {
        // 입력 데이터 검증
        String validationMessage = mentorDetailsUpdateService.validateMentorDetails(updateDto);
        if (validationMessage != null) {
            return ResponseEntity.badRequest().body(new ResponseCode(400));
        }

        mentorDetailsUpdateService.postMentorDetails(updateDto);
        return ResponseEntity.ok(new ResponseCode(200));
    }


    @PatchMapping("/mentordetails/update")
    public ResponseEntity<?> updateMentorDetails(
            @RequestBody MentorDetailsUpdateDto updateDto,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size) {
        Pageable pageable = PageRequest.of(page, size);

        // 입력 데이터 검증
        String validationMessage = mentorDetailsUpdateService.validateMentorDetails(updateDto);
        if (validationMessage != null) {
            return ResponseEntity.badRequest().body(new ResponseCode(400));
        }

        mentorDetailsUpdateService.postMentorDetails(updateDto);
        return ResponseEntity.ok(new ResponseCode(200));
    }


    @DeleteMapping("/mentordetails/update")
    public ResponseEntity<?> deleteMentorDetails(@RequestBody MentorDetailsDeleteDto deleteRequestDto) {
        mentorDetailsUpdateService.deleteMentorDetails(
                deleteRequestDto.getAvailabilityId(),
                deleteRequestDto.getCourseDetailsId());
        return ResponseEntity.ok(new ResponseCode(200));
    }
}






