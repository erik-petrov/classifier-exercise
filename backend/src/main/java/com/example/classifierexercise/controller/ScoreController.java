package com.example.classifierexercise.controller;

import com.example.classifierexercise.entity.Score;
import com.example.classifierexercise.entity.DocumentDTO;
import com.example.classifierexercise.service.ScoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
@Validated
public class ScoreController {
    private final ScoreService scoreService;

    /**
     * GET request which to return all Classifiers from the DB
     * @return All classifiers
     */
    @GetMapping("/classifications")
    public ResponseEntity<List<DocumentDTO>> getAllClassifiers(){
        return ResponseEntity.ok().body(scoreService.getAllScores());
    }

    /**
     * GET request for one of the classifications
     * @param id
     * @return Classification
     */
    @GetMapping("/classifications/{id}")
    public ResponseEntity<DocumentDTO> getAllClassifiers(@PathVariable int id){
        return ResponseEntity.ok().body(scoreService.getScoreById(id));
    }

    /**
     * POST request for receiving Classifiers
     * @return boolean
     */
    @PostMapping(path = "/classifications",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<DocumentDTO> submitClassifier(@RequestBody DocumentDTO doc){
        return new ResponseEntity<>(scoreService.recieveDocument(doc), HttpStatus.CREATED);
    }

    /**
     * PATCH request for updating Classifiers
     * @return boolean
     */
    @PatchMapping("/classifications/{id}")
    public ResponseEntity<DocumentDTO> updateClassifier(@PathVariable int id, @RequestBody DocumentDTO doc){
        return scoreService.updateScore(doc, id);
    }
}
