package com.example.classifierexercise.repository;

import com.example.classifierexercise.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepo extends JpaRepository<Document, Integer> {
}
