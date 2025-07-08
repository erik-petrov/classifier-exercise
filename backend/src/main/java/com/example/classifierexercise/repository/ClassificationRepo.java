package com.example.classifierexercise.repository;

import com.example.classifierexercise.entity.Classification;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class ClassificationRepo {
    private final Map<Integer, Classification> classifications = new ConcurrentHashMap<>();
    private final AtomicInteger id = new AtomicInteger(0);

    public Classification save(Classification cl){
        if(cl.getId() == 0){
            cl.setId(id.incrementAndGet());
        }
        classifications.put(cl.getId(), cl);
        return cl;
    }

    public Optional<Classification> findByLabel(String label){
        return classifications.values().stream().filter(sc -> sc.getLabel().equals(label)).findFirst();
    }
}
