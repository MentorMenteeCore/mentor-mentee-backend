package com.mentormentee.core.controller;

import com.mentormentee.core.dto.MentorDetailsDto;
import com.mentormentee.core.dto.MentorDetailsUpdateDto;
import com.mentormentee.core.dto.ResponseCode;
import com.mentormentee.core.service.MentorInformatoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MentorInformationController {

    private final MentorInformatoinService mentorInformatoinService;

    @GetMapping("/mentordetails")
    public MentorDetailsDto getMentorDetails(@RequestParam String nickName,
                                             @RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "2") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return mentorInformatoinService.getMentorInformationBynickName(nickName, pageable);
    }

    @GetMapping("/mentordetails/update")
    public MentorDetailsUpdateDto getMentorDetails(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return mentorInformatoinService.getMentorDetails(pageable);
    }


    @PatchMapping("/mentordetails/update")
    public ResponseEntity<?> updateMentorDetails(
            @RequestBody MentorDetailsUpdateDto updateDto,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size) {

        // Pageable 객체 생성
        Pageable pageable = PageRequest.of(page, size);

        // 입력 데이터 검증
        String validationMessage = mentorInformatoinService.validateMentorDetails(updateDto);
        if (validationMessage != null) {
            // 응답 메시지를 포함한 형식으로 ResponseEntity 반환
            return ResponseEntity.badRequest().body(
                    Map.of("code", 400, "message", validationMessage)
            );
        }

        // 멘토 정보 업데이트
        mentorInformatoinService.updateMentorDetails(updateDto, pageable);

        return ResponseEntity.ok(new ResponseCode(200));
    }

}












