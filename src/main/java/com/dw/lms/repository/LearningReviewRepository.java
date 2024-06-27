package com.dw.lms.repository;

import com.dw.lms.model.Learning_review;
import com.dw.lms.model.CK.Learning_review_CK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LearningReviewRepository extends JpaRepository<Learning_review, Learning_review_CK> {
}
