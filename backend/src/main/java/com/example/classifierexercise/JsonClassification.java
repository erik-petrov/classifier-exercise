package com.example.classifierexercise;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JsonClassification {
    private String label;
    private double score;

    @JsonProperty("label")
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @JsonProperty("score")
    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
