package com.example.classifierexercise;

import com.example.classifierexercise.repository.ClassificationRepo;
import com.example.classifierexercise.repository.DocumentRepo;
import com.example.classifierexercise.repository.ScoreRepo;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {
    private final ClassificationRepo classificationRepo;
    private final ScoreRepo scoreRepo;
    private final DocumentRepo documentRepo;


    public DataInitializer(ClassificationRepo classificationRepo, ScoreRepo scoreRepo, DocumentRepo documentRepo) {
        this.classificationRepo = classificationRepo;
        this.scoreRepo = scoreRepo;
        this.documentRepo = documentRepo;
    }
}
