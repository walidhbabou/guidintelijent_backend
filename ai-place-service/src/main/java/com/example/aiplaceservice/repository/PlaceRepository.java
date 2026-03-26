package com.example.aiplaceservice.repository;

import com.example.aiplaceservice.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {

    Optional<Place> findByPlaceId(String placeId);

    List<Place> findByCity(String city);

    List<Place> findByCategory(String category);

    List<Place> findByCityAndCategory(String city, String category);

    List<Place> findByNameContainingIgnoreCase(String name);

}

