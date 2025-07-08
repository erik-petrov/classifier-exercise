package com.example.classifierexercise.service;

import com.example.classifierexercise.entity.*;
import com.example.classifierexercise.repository.ClassificationRepo;
import com.example.classifierexercise.repository.DocumentRepo;
import com.example.classifierexercise.repository.ScoreRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.method.support.CompositeUriComponentsContributor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ScoreService {
    private final ScoreRepo scoreRepo;
    private final DocumentRepo documentRepo;
    private final ClassificationRepo classificationRepo;
    private final CompositeUriComponentsContributor compositeUriComponentsContributor;

    public ScoreService(ScoreRepo scoreRepo, DocumentRepo documentRepo, ClassificationRepo classificationRepo, CompositeUriComponentsContributor compositeUriComponentsContributor) {
        this.scoreRepo = scoreRepo;
        this.documentRepo = documentRepo;
        this.classificationRepo = classificationRepo;
        this.compositeUriComponentsContributor = compositeUriComponentsContributor;
    }

    public DocumentDTO getScoreById(int id) {
        return convertToDto(documentRepo.findById(id));
    }

    public List<DocumentDTO> getAllScores() {
        return documentRepo.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private DocumentDTO convertToDto(Document doc) {
        DocumentDTO dto = new DocumentDTO(doc);
        dto.setId(doc.getId());
        dto.setClassifications(
            doc.getScores().stream()
                    .map(ScoreDTO::new)
                    .collect(Collectors.toList())
        );
        return dto;
    }

    private Document convertToDoc(DocumentDTO dto){
        Document doc = new Document(dto.getDocument_name());
        List<Score> scl = new ArrayList<>();
        dto.getClassifications().forEach(sc -> {
            Classification cl = new Classification(sc.getLabel());
            classificationRepo.save(cl);
            scl.add(new Score(doc, cl, sc.getScore()));
        });
        doc.setScores(scl);
        return doc;
    }

    public DocumentDTO recieveDocument(DocumentDTO d) {
        try{
            Document newDoc = new Document(d.getDocument_name());
            documentRepo.save(newDoc);
            for (ScoreDTO s : d.getClassifications()){
                Classification newCl = new Classification(s.getLabel());
                classificationRepo.save(newCl);
                scoreRepo.save(new Score(newDoc, newCl, s.getScore()));
            }

        }catch (Exception e){
            return null;
        }
        return d;
    }

    public ResponseEntity<DocumentDTO> updateScore(DocumentDTO dto, int id) {
        try{
            documentRepo.updateDoc(convertToDoc(dto), id);
        }catch(Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(convertToDto(documentRepo.findById(id)));
    }
}
