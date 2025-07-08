package com.example.classifierexercise.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScoreDTO {
    private String label;
    private double score;

    public ScoreDTO(Score sc) {
        this.score = sc.getScore();
        this.label = sc.getClassification().getLabel();
    }
}
