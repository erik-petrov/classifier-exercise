package com.example.classifierexercise.repository;

import com.example.classifierexercise.entity.Document;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class DocumentRepo {
    private final Map<Integer, Document> docs = new ConcurrentHashMap<>();
    private final AtomicInteger id = new AtomicInteger(0);
}
