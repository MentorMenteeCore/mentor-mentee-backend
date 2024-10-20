package com.mentormentee.core.token.controller;


import com.mentormentee.core.token.dto.AuthToken;
import com.mentormentee.core.token.dto.RefreshDto;
import com.mentormentee.core.token.service.RefreshService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequiredArgsConstructor
@RequestMapping("/api/refresh")
@Tag(name = "refresh")
public class RefreshController {
    private final RefreshService refreshService;
    @Operation(description = "해당 url로 요청 시 access token 재발급")
    @PostMapping
    public ResponseEntity<AuthToken> refresh(@RequestBody RefreshDto refreshDto) throws ParseException {
        return ResponseEntity.ok(refreshService.refresh(refreshDto));
    }
}
