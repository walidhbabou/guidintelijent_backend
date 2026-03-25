package com.example.aiplaceservice.service;

import com.example.aiplaceservice.client.MoroccoAiGuideClient;
import com.example.aiplaceservice.dto.morocco.AskRequestDto;
import com.example.aiplaceservice.dto.morocco.AskResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MoroccoAiGuideService {

    private final MoroccoAiGuideClient moroccoAiGuideClient;
    
    public AskResponseDto ask(AskRequestDto request) {
        log.info("Calling Morocco AI Guide API with question: {}", request.getQuestion());
        return moroccoAiGuideClient.ask(request);
    }
}

