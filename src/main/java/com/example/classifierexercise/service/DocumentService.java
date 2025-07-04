package com.example.classifierexercise.service;

import com.example.classifierexercise.entity.Document;
import com.example.classifierexercise.repository.DocumentRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Business logic service
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class DocumentService {
    private final DocumentRepo documentRepo;

    public List<Document> getDocuments() {
        return documentRepo.findAll();
    }

    public Document SaveDocument(Document document) {
        log.info("Updating document: {}", document.getId());
        return documentRepo.save(document);
    }
}
