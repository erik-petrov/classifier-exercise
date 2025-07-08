package com.example.classifierexercise.repository;

import com.example.classifierexercise.entity.Score;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class ScoreRepo {
    private final Map<Integer, Score> scores = new ConcurrentHashMap<>();
    private final AtomicInteger id = new AtomicInteger(0);

    public List<Score> findAll(){
        return List.copyOf(scores.values());
    }

    public Score save(Score sc) {
        if(sc.getId() == 0){
            sc.setId(id.incrementAndGet());
        }
        return scores.put(sc.getId(), sc);
    }

    public Score update(Score sc, int id) {
        return scores.put(id, sc);
    }

    public Score findById(int id) {
        return scores.get(id);
    }

    public List<Score> findByDocumentId(int docId) {
        return scores.values().stream()
                .filter(sc -> sc.getDocument().getId()== docId)
                .collect(Collectors.toList());
    }
}
