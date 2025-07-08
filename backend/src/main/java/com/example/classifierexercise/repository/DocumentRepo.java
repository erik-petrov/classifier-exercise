package com.example.classifierexercise.repository;

import com.example.classifierexercise.entity.Document;
import com.example.classifierexercise.entity.DocumentDTO;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class DocumentRepo {
    private final Map<Integer, Document> docs = new ConcurrentHashMap<>();
    private final AtomicInteger id = new AtomicInteger(0);
    private final ScoreRepo scoreRepo;
    private final ClassificationRepo classificationRepo;

    public DocumentRepo(ScoreRepo scoreRepo, ClassificationRepo classificationRepo) {
        this.scoreRepo = scoreRepo;
        this.classificationRepo = classificationRepo;
    }

    public Document save(Document doc){
        if(doc.getId() == 0){
            doc.setId(id.incrementAndGet());
        }
        docs.put(doc.getId(), doc);
        return doc;
    }

    public List<Document> findAll(){
        return new ArrayList<>(docs.values());
    }

    public void updateDoc(Document document, int id) {
        Document oldDoc = docs.get(id);
        oldDoc.setName(document.getName() != null ? document.getName() : oldDoc.getName());
        oldDoc.setScores(document.getScores() != null ? document.getScores() : oldDoc.getScores());
        scoreRepo.updateScores(oldDoc.getScores(), document.getScores());
        docs.put(id, oldDoc);
    }

    public Document findById(int id){
        return docs.get(id);
    }

    public Optional<Document> findByName(String name){
        return docs.values().stream().filter(d -> d.getName().equals(name)).findFirst();
    }

    public boolean changeDocumentSelection(Document doc, String label) {
        try{
            doc.setChosenClassificationId(label);
        }catch (Exception e){
            System.out.println(Arrays.toString(e.getStackTrace()));
            return false;
        }
        return true;
    }
}
