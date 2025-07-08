package com.example.classifierexercise;

import com.example.classifierexercise.entity.Classification;
import com.example.classifierexercise.entity.Document;
import com.example.classifierexercise.entity.Score;
import com.example.classifierexercise.repository.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {
    private final ClassificationRepo classificationRepo;
    private final ScoreRepo scoreRepo;
    private final DocumentRepo documentRepo;

    public DataInitializer(ClassificationRepo classificationRepo, ScoreRepo scoreRepo, DocumentRepo documentRepo) {
        this.classificationRepo = classificationRepo;
        this.scoreRepo = scoreRepo;
        this.documentRepo = documentRepo;
    }

    @Override
    public void run(String... args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<JsonFileEntry>> typeReference = new TypeReference<>() {};
        InputStream inputStream = TypeReference.class.getResourceAsStream("/static/example-classification.json");

        try {
            List<JsonFileEntry> fileEntries = mapper.readValue(inputStream, typeReference);

            for (JsonFileEntry entry : fileEntries) {
                Document document = documentRepo.findByName(entry.getDocumentName())
                        .orElseGet(() -> documentRepo.save(new Document(entry.getDocumentName())));

                for (JsonClassification jsonClass : entry.getClassifications()) {
                    Classification classification = classificationRepo.findByLabel(jsonClass.getLabel())
                            .orElseGet(() -> classificationRepo.save(new Classification(jsonClass.getLabel())));

                    Score newScore = new Score();
                    newScore.setClassification(classification);
                    newScore.setScore(jsonClass.getScore());

                    document.addScore(newScore);
                    scoreRepo.save(newScore);
                }
            }
        } catch (Exception e) {
            System.out.println("Unable to import data: " + e.getMessage());
        }
    }
}
