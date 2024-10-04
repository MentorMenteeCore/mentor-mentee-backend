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
@RequestMapping("/api/mentordetails")
public class MentorDetailsUpdateController {

    private final MentorDetailsUpdateService mentorDetailsUpdateService;

    @GetMapping("/update/{userId}")
    public ResponseEntity<MentorDetailsDto> getMentorDetails(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size) {
        Pageable pageable = PageRequest.of(page, size);
        try {
            MentorDetailsDto mentorDetails = mentorDetailsUpdateService.getMentorDetails(userId, pageable);
            return ResponseEntity.ok(mentorDetails);
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/update/{userId}")
    public ResponseEntity<String> postMentorDetails(
            @PathVariable Long userId,
            @RequestBody MentorDetailsUpdateDto updateDto) {

        // 사용자 존재 확인 및 권한 검증
        String validationMessage = mentorDetailsUpdateService.validateMentorDetails(updateDto);
        if (validationMessage != null) {
            return ResponseEntity.badRequest().body(validationMessage);
        }

        try {
            mentorDetailsUpdateService.postMentorDetails(userId, updateDto);
            return ResponseEntity.ok("멘토 정보가 성공적으로 추가되었습니다.");
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PatchMapping("/update/{userId}")
    public ResponseEntity<MentorDetailsDto> updateMentorDetails(
            @PathVariable Long userId,
            @RequestBody MentorDetailsUpdateDto updateDto,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size) {
        Pageable pageable = PageRequest.of(page, size);

        // 입력 데이터 검증
        String validationMessage = mentorDetailsUpdateService.validateMentorDetails(updateDto);
        if (validationMessage != null) {
            return ResponseEntity.badRequest().body(null);
        }

        try {
            MentorDetailsDto updatedDetails = mentorDetailsUpdateService.updateMentorDetails(userId, updateDto, pageable);
            return ResponseEntity.ok(updatedDetails);
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/update/{userId}")
    public ResponseEntity<String> deleteMentorDetails(
            @PathVariable Long userId,
            @RequestBody MentorDetailsDeleteDto deleteRequestDto) {
        try {
            mentorDetailsUpdateService.deleteMentorDetails(
                    userId,
                    deleteRequestDto.getAvailabilityId(),
                    deleteRequestDto.getCourseDetailsId());
            return ResponseEntity.ok("멘토 정보가 성공적으로 삭제되었습니다.");
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PatchMapping("/changerole/{userId}")
    public ResponseEntity<String> changeRoleToMentee(@PathVariable Long userId) {
        try {
            mentorDetailsUpdateService.changeRoleToMentee(userId);
            return ResponseEntity.ok("역할이 멘티로 변경되었습니다.");
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}





