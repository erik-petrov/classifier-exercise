package com.example.classifierexercise.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class DocumentDTO {
    private int id;
    private String document_name;
    private List<ScoreDTO> classifications;

    public DocumentDTO(Document document) {
        this.document_name = document.getName();
        this.classifications = new ArrayList<>();
        document.getScores().forEach(score -> this.classifications.add(new ScoreDTO(score)));
    }
}
