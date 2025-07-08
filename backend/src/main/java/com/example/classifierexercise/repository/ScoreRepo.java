package com.example.classifierexercise.repository;

import com.example.classifierexercise.entity.Score;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class ScoreRepo {
    private final Map<Integer, Score> scores = new ConcurrentHashMap<>();
    private final AtomicInteger id = new AtomicInteger(0);


    public Score save(Score sc) {
        if(sc.getId() == 0){
            sc.setId(id.incrementAndGet());
        }
        return scores.put(sc.getId(), sc);
    }

    public void updateScores(List<Score> oldList, List<Score> newList){
        for(Score sc : oldList){
            if(sc.getId() == 0) {               //if new
                sc.setId(id.incrementAndGet());
                scores.put(sc.getId(), sc);
            }else if(newList.contains(sc)){     //if existing
                scores.put(sc.getId(), sc);
            }else{                              //else -> deleted
                scores.remove(sc.getId());
            }
        }

    }
}
