package com.example.aiplaceservice.controller;

import com.example.aiplaceservice.dto.morocco.AskRequestDto;
import com.example.aiplaceservice.dto.morocco.AskResponseDto;
import com.example.aiplaceservice.service.MoroccoAiGuideService;
import com.example.aiplaceservice.service.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/morocco-ai")
@RequiredArgsConstructor
public class MoroccoAiGuideController {

    private final MoroccoAiGuideService moroccoAiGuideService;
    private final PlaceService placeService;


    @PostMapping("/ask")
    public ResponseEntity<AskResponseDto> ask(@RequestBody AskRequestDto request) {
        AskResponseDto response = moroccoAiGuideService.ask(request);
        // Sauvegarder les places retournées dans la base de données
        placeService.savePlacesFromResponse(response);
        return ResponseEntity.ok(response);
    }

    /**
     * Récupère toutes les places stockées dans la base de données
     */
    @GetMapping("/places")
    public ResponseEntity<AskResponseDto> getAllPlaces(
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String category) {
        AskResponseDto response = placeService.getAllPlaces(city, category);
        return ResponseEntity.ok(response);
    }

    /**
     * Récupère les places par ville
     */
    @GetMapping("/places/by-city/{city}")
    public ResponseEntity<AskResponseDto> getPlacesByCity(@PathVariable String city) {
        AskResponseDto response = placeService.getAllPlaces(city, null);
        return ResponseEntity.ok(response);
    }

    /**
     * Récupère les places par catégorie
     */
    @GetMapping("/places/by-category/{category}")
    public ResponseEntity<AskResponseDto> getPlacesByCategory(@PathVariable String category) {
        AskResponseDto response = placeService.getAllPlaces(null, category);
        return ResponseEntity.ok(response);
    }
}

