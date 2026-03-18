package com.example.coreservice.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {
    private Long id;
    private Long userId;
    private Long placeId;
    private String comment;
    private Double rating;
    private String createdAt;
    private String updatedAt;
}
