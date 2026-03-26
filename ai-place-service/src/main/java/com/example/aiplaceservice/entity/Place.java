package com.example.aiplaceservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "places")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    private Double rating;

    @ElementCollection
    @CollectionTable(name = "place_types", joinColumns = @JoinColumn(name = "place_id"))
    @Column(name = "type")
    private List<String> types;

    private String photoUrl;

    @Column(nullable = false, unique = true)
    private String placeId;

    private String googleMapsUrl;

    private String city;

    private String category;

    @ElementCollection
    @CollectionTable(name = "place_preferences", joinColumns = @JoinColumn(name = "place_id"))
    @Column(name = "preference")
    private List<String> preferences;

}

