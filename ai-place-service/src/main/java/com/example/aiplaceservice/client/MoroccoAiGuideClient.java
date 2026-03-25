package com.example.aiplaceservice.client;

import com.example.aiplaceservice.dto.morocco.AskRequestDto;
import com.example.aiplaceservice.dto.morocco.AskResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "morocco-ai-guide-client",
        url = "${morocco-ai-guide.base-url:https://llm-ai-guid.onrender.com}"
)
public interface MoroccoAiGuideClient {

    @PostMapping("/api/ai/search")
    AskResponseDto ask(@RequestBody AskRequestDto request);
}

