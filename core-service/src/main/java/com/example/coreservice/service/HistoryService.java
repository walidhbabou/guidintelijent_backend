package com.example.coreservice.service;

import com.example.coreservice.entity.History;
import com.example.coreservice.Repo.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class HistoryService {

    @Autowired
    private HistoryRepository historyRepository;

    public History save(History history) {
        return historyRepository.save(history);
    }

    public List<History> findByUserId(Long userId) {
        return historyRepository.findByUserId(userId);
    }

    public void delete(Long id) {
        historyRepository.deleteById(id);
    }
}
