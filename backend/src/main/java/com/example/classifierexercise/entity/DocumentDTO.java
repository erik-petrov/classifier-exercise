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
    private String chosenClassificationId = "";

    public DocumentDTO(Document document) {
        this.document_name = document.getName();
        this.classifications = new ArrayList<>();
        this.chosenClassificationId = document.getChosenClassificationId();
        document.getScores().forEach(score -> this.classifications.add(new ScoreDTO(score)));
    }
}
