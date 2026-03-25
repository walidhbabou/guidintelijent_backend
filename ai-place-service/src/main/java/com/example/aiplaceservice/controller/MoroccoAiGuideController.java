package com.example.aiplaceservice.controller;

import com.example.aiplaceservice.dto.morocco.AskRequestDto;
import com.example.aiplaceservice.dto.morocco.AskResponseDto;
import com.example.aiplaceservice.service.MoroccoAiGuideService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/morocco-ai")
@RequiredArgsConstructor
public class MoroccoAiGuideController {

    private final MoroccoAiGuideService moroccoAiGuideService;


    @PostMapping("/ask")
    public ResponseEntity<AskResponseDto> ask(@RequestBody AskRequestDto request) {
        AskResponseDto response = moroccoAiGuideService.ask(request);
        return ResponseEntity.ok(response);
    }
}

