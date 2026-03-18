package com.example.coreservice.controller;

import com.example.coreservice.entity.History;
import com.example.coreservice.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/history")
public class HistoryController {

    @Autowired
    private HistoryService historyService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<History>> getUserHistory(@PathVariable Long userId) {
        return ResponseEntity.ok(historyService.findByUserId(userId));
    }

    @PostMapping
    public ResponseEntity<History> create(@RequestBody History history) {
        return ResponseEntity.ok(historyService.save(history));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        historyService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
