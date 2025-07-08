package com.example.classifierexercise.controller;

import com.example.classifierexercise.entity.Score;
import com.example.classifierexercise.service.ScoreService;
import lombok.RequiredArgsConstructor;
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
    private final ScoreService classifierService;

    /**
     * GET request which to return all Classifiers from the DB
     * @return All classifiers
     */
    @GetMapping("/classifications")
    public ResponseEntity<List<Score>> getAllClassifiers(){
        return ResponseEntity.ok().body(classifierService.getAllScores());
    }

    /**
     * POST request for receiving Classifiers
     * @return boolean
     */
    @PostMapping(path = "/classifications",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Boolean> submitClassifier(@RequestBody Score cl){
        return ResponseEntity.ok().body(classifierService.putScore(cl));
    }

    /**
     * PATCH request for updating Classifiers
     * @return boolean
     */
    @PatchMapping("/classifications/{id}")
    public ResponseEntity<Boolean> updateClassifier(@PathVariable Integer id, @RequestBody Score cl){
        return ResponseEntity.ok().body(classifierService.updateScore(cl ,id));
    }
}
