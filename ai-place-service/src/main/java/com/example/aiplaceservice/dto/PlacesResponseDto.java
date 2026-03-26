package com.example.aiplaceservice.dto;

import com.example.aiplaceservice.dto.morocco.AskResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlacesResponseDto {

    private int results_count;
    private AskResponseDto data;
    private String message;
    private boolean success;

}

