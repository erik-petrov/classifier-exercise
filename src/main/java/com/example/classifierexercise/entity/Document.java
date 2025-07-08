package com.example.classifierexercise.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(callSuper=false)
@Getter
public class Document {
    private int id;
    private String name;
}