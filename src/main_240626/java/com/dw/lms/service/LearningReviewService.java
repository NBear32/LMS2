package com.dw.lms.service;

import com.dw.lms.model.Course_registration;
import com.dw.lms.model.Learning_review;
import com.dw.lms.repository.LearningReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LearningReviewService {
    @Autowired
    LearningReviewRepository learningReviewRepository;

    public List<Learning_review> getAllReview() {
        return learningReviewRepository.findAll();
    }
}
