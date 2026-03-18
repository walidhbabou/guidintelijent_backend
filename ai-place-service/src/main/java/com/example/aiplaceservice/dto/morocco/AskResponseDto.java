package com.example.aiplaceservice.dto.morocco;

import lombok.Data;

import java.util.List;

@Data
public class AskResponseDto {

    private String answer;
    private Integer count;
    private String dataset_file;
    private Filters filters;
    private List<MatchItem> matches;

    @Data
    public static class Filters {
        private String category;
        private String city;
    }

    @Data
    public static class MatchItem {
        private String name;
        private String category;
        private String city;
        private String lat_lon;
        private Double latitude;
        private Double longitude;
        private String address;
        private Double score;
    }
}
