package com.example.classifierexercise.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode
@Getter
@Setter
public class Classification {
    private int id;
    private String label;

    public Classification(String label) {
        this.label = label;
    }

    public Classification(Classification classification) {
        this.label = classification.getLabel();
        this.id = classification.getId();
    }
}
