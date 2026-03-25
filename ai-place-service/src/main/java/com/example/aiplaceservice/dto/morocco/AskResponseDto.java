package com.example.aiplaceservice.dto.morocco;

import lombok.Data;

import java.util.List;

@Data
public class AskResponseDto {

    private String intent;
    private String city;
    private String category;
    private List<String> preferences;
    private Integer result_limit;
    private Boolean near_me;
    private Integer results_count;
    private List<ResultItem> results;
    private String message;

    @Data
    public static class ResultItem {
        private String name;
        private String description;
        private String address;
        private Double latitude;
        private Double longitude;
        private Double rating;
        private List<String> types;
        private String photo_url;
        private String place_id;
        private String google_maps_url;
    }
}
