package com.example.aiplaceservice.controller;

import com.example.aiplaceservice.dto.morocco.AskResponseDto;
import com.example.aiplaceservice.entity.Place;
import com.example.aiplaceservice.service.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/places")
@RequiredArgsConstructor
public class PlaceController {

    private final PlaceService placeService;

    /**
     * Récupère toutes les places avec filtres optionnels (city, category, q)
     * q = recherche par nom
     */
    @GetMapping
    public ResponseEntity<AskResponseDto> getPlaces(
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String q) {
        return ResponseEntity.ok(placeService.getAllPlaces(city, category, q));
    }

    /**
     * Crée une nouvelle place
     */
    @PostMapping
    public ResponseEntity<Place> createPlace(@RequestBody Place place) {
        Place savedPlace = placeService.savePlace(place);
        return ResponseEntity.ok(savedPlace);
    }

    /**
     * Récupère une place par son ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Place> getPlace(@PathVariable Long id) {
        Place place = placeService.getPlaceById(id);
        if (place != null) {
            return ResponseEntity.ok(place);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Récupère une place par son place_id (identifiant Google)
     */
    @GetMapping("/by-place-id/{placeId}")
    public ResponseEntity<Place> getPlaceByPlaceId(@PathVariable String placeId) {
        Place place = placeService.getPlaceByPlaceId(placeId);
        if (place != null) {
            return ResponseEntity.ok(place);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Supprime une place
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlace(@PathVariable Long id) {
        placeService.deletePlace(id);
        return ResponseEntity.noContent().build();
    }

}

