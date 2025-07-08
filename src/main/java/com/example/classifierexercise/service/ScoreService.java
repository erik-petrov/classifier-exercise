package com.example.classifierexercise.service;

import com.example.classifierexercise.entity.*;
import com.example.classifierexercise.repository.DocumentRepo;
import com.example.classifierexercise.repository.ScoreRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ScoreService {
    private final ScoreRepo scoreRepo;
    private final DocumentRepo documentRepo;

    public ScoreService(ScoreRepo scoreRepo, DocumentRepo documentRepo) {
        this.scoreRepo = scoreRepo;
        this.documentRepo = documentRepo;
    }

    public List<DocumentDTO> getAllScores() {
        return documentRepo.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private DocumentDTO convertToDto(Document doc) {
        DocumentDTO dto = new DocumentDTO(doc);
        dto.setClassifications(
            scoreRepo.findByDocumentId(doc.getId()).stream()
                    .map(ScoreDTO::new)
                    .collect(Collectors.toList())
        );
        return dto;
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
