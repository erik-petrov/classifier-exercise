package com.example.classifierexercise.service;

import com.example.classifierexercise.entity.*;
import com.example.classifierexercise.repository.ClassificationRepo;
import com.example.classifierexercise.repository.DocumentRepo;
import com.example.classifierexercise.repository.ScoreRepo;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ScoreService {
    private final ScoreRepo scoreRepo;

    public ScoreService(ScoreRepo scoreRepo) {
        this.scoreRepo = scoreRepo;
    }

    public List<Score> getAllScores() {
        return scoreRepo.findAll();
    }

    public boolean putScore(Score sc){
        try{
            scoreRepo.save(sc);
        }catch(Exception e){
            log.error(e.getMessage());
            return false;
        }
        return true;
    }

    public boolean updateScore(Score sc, int id) {
        try{
            scoreRepo.update(sc, id);
        }catch(Exception e){
            log.error(e.getMessage());
            return false;
        }
        return true;
    }
}
