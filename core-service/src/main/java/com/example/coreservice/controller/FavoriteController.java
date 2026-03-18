package com.example.coreservice.controller;

import com.example.coreservice.entity.Favorite;
import com.example.coreservice.service.FavoriteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/favorites")
@Tag(name = "Favorites", description = "Gestion des favoris")
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;

    @PostMapping
    @Operation(summary = "Ajouter un favori")
    public ResponseEntity<Favorite> create(@RequestBody Favorite favorite) {
        return ResponseEntity.ok(favoriteService.save(favorite));
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Récupérer les favoris d'un utilisateur")
    public ResponseEntity<List<Favorite>> getUserFavorites(@PathVariable Long userId) {
        return ResponseEntity.ok(favoriteService.findByUserId(userId));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un favori")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        favoriteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
