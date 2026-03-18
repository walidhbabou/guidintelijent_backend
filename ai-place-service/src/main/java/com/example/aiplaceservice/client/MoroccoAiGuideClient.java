package com.example.aiplaceservice.client;

import com.example.aiplaceservice.dto.morocco.AskRequestDto;
import com.example.aiplaceservice.dto.morocco.AskResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "morocco-ai-guide-client",
        url = "${morocco-ai-guide.base-url:https://ai-scraping-data.onrender.com}"
)
public interface MoroccoAiGuideClient {

    @GetMapping("/")
    String root();

    @GetMapping("/health")
    String health();

    @PostMapping("/ask")
    AskResponseDto ask(@RequestBody AskRequestDto request);
}

