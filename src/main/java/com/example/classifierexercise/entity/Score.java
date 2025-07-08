package com.example.classifierexercise.entity;

import lombok.Data;

@Data
public class Score {
    private int id;
    private Document document;
    private Classification classification;
    private double score;
}
