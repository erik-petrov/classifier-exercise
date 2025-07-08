package com.example.classifierexercise;

import com.example.classifierexercise.entity.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class ClassifierExerciseApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void whenGetAllClassifications_thenReturnsOkAndListOfDocuments() throws Exception {
        mockMvc.perform(get("/classifications"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()", greaterThan(0)))
                .andExpect(jsonPath("$[4].document_name", is("Contract Draft")));
    }

    @Test
    void whenPostNewDocument_thenReturnsCreated() throws Exception {
        // 1. Create the DTOs for the request body
        ScoreDTO newScore = new ScoreDTO();
        newScore.setLabel("Test Label");
        newScore.setScore(0.99);

        DocumentDTO newDocument = new DocumentDTO();
        newDocument.setDocument_name("New Test Document");
        newDocument.setClassifications(List.of(newScore));

        // 2. Perform the POST request
        mockMvc.perform(post("/classifications")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newDocument)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void whenPatchDocument_thenReturnsOkAndIsUpdated() throws Exception {
        Document toPatch = new Document("Patch Test Document");
        toPatch.setScores(List.of(
                new Score(toPatch, new Classification("Test"), 1)
        ));

        DocumentDTO patchRequest = new DocumentDTO(toPatch);

        mockMvc.perform(patch("/classifications/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patchRequest)))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(get("/classifications/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.document_name", is("Patch Test Document")))
                .andExpect(jsonPath("$.classifications", hasSize(1)));
    }

}
