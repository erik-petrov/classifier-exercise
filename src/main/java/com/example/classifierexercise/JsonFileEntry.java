package com.example.classifierexercise;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class JsonFileEntry {
    private String documentName;
    private List<JsonClassification> classifications;

    @JsonProperty("document_name")
    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    @JsonProperty("classifications")
    public List<JsonClassification> getClassifications() {
        return classifications;
    }

    public void setClassifications(List<JsonClassification> classifications) {
        this.classifications = classifications;
    }
}