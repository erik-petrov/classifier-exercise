package com.example.classifierexercise.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class DocumentDTO {
    private String document_name;
    private List<ScoreDTO> classifications;

    public DocumentDTO(Document document) {
        this.document_name = document.getName();
    }
}
