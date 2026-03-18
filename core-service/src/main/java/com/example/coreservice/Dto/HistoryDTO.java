package com.example.coreservice.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoryDTO {
    private Long id;
    private Long userId;
    private Long placeId;
    private String action;
    private String visitedAt;
}
