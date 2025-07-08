package com.example.classifierexercise.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Score {
    private int id;
    private Document document;
    private Classification classification;
    private double score;

    public Score(Document newDoc, Classification newCl, double score) {
        document = newDoc;
        classification = newCl;
        this.score = score;
    }
}
