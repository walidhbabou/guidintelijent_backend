package com.example.aiplaceservice.service;

import com.example.aiplaceservice.dto.morocco.AskResponseDto;
import com.example.aiplaceservice.entity.Place;
import com.example.aiplaceservice.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlaceService {

    private final PlaceRepository placeRepository;

    /**
     * Récupère toutes les places depuis la base de données
     */
    public AskResponseDto getAllPlaces(String city, String category) {
        List<Place> places;

        if (city != null && category != null) {
            places = placeRepository.findByCityAndCategory(city, category);
        } else if (city != null) {
            places = placeRepository.findByCity(city);
        } else if (category != null) {
            places = placeRepository.findByCategory(category);
        } else {
            places = placeRepository.findAll();
        }

        return buildResponse(places, city, category);
    }

    /**
     * Récupère une place par son ID
     */
    public Place getPlaceById(Long id) {
        return placeRepository.findById(id).orElse(null);
    }

    /**
     * Récupère une place par son placeId (identifiant Google)
     */
    public Place getPlaceByPlaceId(String placeId) {
        return placeRepository.findByPlaceId(placeId).orElse(null);
    }

    /**
     * Crée ou met à jour une place
     */
    public Place savePlace(Place place) {
        return placeRepository.save(place);
    }

    /**
     * Sauvegarde plusieurs places depuis une réponse AskResponseDto
     */
    public void savePlacesFromResponse(AskResponseDto response) {
        if (response.getResults() != null && !response.getResults().isEmpty()) {
            for (AskResponseDto.ResultItem item : response.getResults()) {
                Place place = Place.builder()
                        .name(item.getName())
                        .description(item.getDescription())
                        .address(item.getAddress())
                        .latitude(item.getLatitude())
                        .longitude(item.getLongitude())
                        .rating(item.getRating())
                        .types(item.getTypes())
                        .photoUrl(item.getPhoto_url())
                        .placeId(item.getPlace_id())
                        .googleMapsUrl(item.getGoogle_maps_url())
                        .city(response.getCity())
                        .category(response.getCategory())
                        .preferences(response.getPreferences())
                        .build();

                // Vérifier si la place existe déjà
                if (placeRepository.findByPlaceId(item.getPlace_id()).isEmpty()) {
                    placeRepository.save(place);
                }
            }
        }
    }

    /**
     * Supprime une place
     */
    public void deletePlace(Long id) {
        placeRepository.deleteById(id);
    }

    /**
     * Construit une réponse AskResponseDto à partir des places
     */
    private AskResponseDto buildResponse(List<Place> places, String city, String category) {
        AskResponseDto response = new AskResponseDto();
        response.setCity(city);
        response.setCategory(category);
        response.setResults_count(places.size());

        List<AskResponseDto.ResultItem> resultItems = places.stream()
                .map(this::convertPlaceToResultItem)
                .collect(Collectors.toList());

        response.setResults(resultItems);
        response.setMessage(places.isEmpty() ? "Aucune place trouvée" : "Places récupérées avec succès");

        return response;
    }

    /**
     * Convertit une entité Place en ResultItem
     */
    private AskResponseDto.ResultItem convertPlaceToResultItem(Place place) {
        AskResponseDto.ResultItem item = new AskResponseDto.ResultItem();
        item.setName(place.getName());
        item.setDescription(place.getDescription());
        item.setAddress(place.getAddress());
        item.setLatitude(place.getLatitude());
        item.setLongitude(place.getLongitude());
        item.setRating(place.getRating());
        item.setTypes(place.getTypes());
        item.setPhoto_url(place.getPhotoUrl());
        item.setPlace_id(place.getPlaceId());
        item.setGoogle_maps_url(place.getGoogleMapsUrl());
        return item;
    }

}

