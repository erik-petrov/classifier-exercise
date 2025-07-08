package com.example.classifierexercise.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@EqualsAndHashCode(callSuper=false)
@Getter
@Setter
public class Document {
    private int id;
    private String name;
    private List<Score> scores = new ArrayList<>();
    private String chosenClassificationId = "";

    public void addScore(Score score) {
        this.scores.add(score);
        score.setDocument(this);
    }

    public Document(String name) {
        this.name = name;
    }
}