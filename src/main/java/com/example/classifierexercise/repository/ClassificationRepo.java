package com.example.classifierexercise.repository;

import com.example.classifierexercise.entity.Classification;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class ClassificationRepo {
    private final Map<Integer, Classification> scores = new ConcurrentHashMap<>();
    private final AtomicInteger id = new AtomicInteger(0);
}
