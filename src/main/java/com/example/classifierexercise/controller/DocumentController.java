package com.example.classifierexercise.controller;

import com.example.classifierexercise.entity.Document;
import com.example.classifierexercise.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
@Validated
public class DocumentController {
    private final DocumentService documentService;

    /**
     * GET req. which
     * @return All documents
     */
    @GetMapping("/")
    public ResponseEntity<List<Document>> getAllDocuments(){
        return ResponseEntity.ok().body(documentService.getDocuments());
    }
}
