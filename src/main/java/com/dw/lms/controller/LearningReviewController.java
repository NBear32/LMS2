package com.dw.lms.controller;

import com.dw.lms.model.*;
import com.dw.lms.service.LearningReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LearningReviewController {
    @Autowired
    LearningReviewService learningReviewService;

    @GetMapping("/learning/review")
    public ResponseEntity<List<Learning_review>> getAllReview() {
        return new ResponseEntity<>(learningReviewService.getAllReview(),
                HttpStatus.OK);
    }

    @GetMapping("/learning/review/{lectureId}")
    public ResponseEntity<List<Learning_review>> getReviewByLectureId(@PathVariable String lectureId){
        return new ResponseEntity<>(learningReviewService.getReviewByLectureId(lectureId),HttpStatus.OK);
    }

		@GetMapping("/learning/review/{lectureId}/{userId}")
    public ResponseEntity<List<Learning_review>> getReviewByLectureIdUserId(@PathVariable String lectureId, @PathVariable String userId){
        return new ResponseEntity<>(learningReviewService.getReviewByLectureIdUserId(lectureId, userId),HttpStatus.OK);
    }

    @PostMapping("/learning/saveReview")
    public ResponseEntity<String> saveReview(@RequestBody Learning_review learning_review) {
        return new ResponseEntity<>(learningReviewService.saveReview(learning_review),
                HttpStatus.OK);
    }

}
