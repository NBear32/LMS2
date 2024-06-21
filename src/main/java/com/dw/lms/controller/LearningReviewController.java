package com.dw.lms.controller;

import com.dw.lms.model.Course_registration;
import com.dw.lms.model.Learning_review;
import com.dw.lms.repository.LearningReviewRepository;
import com.dw.lms.service.LearningReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
