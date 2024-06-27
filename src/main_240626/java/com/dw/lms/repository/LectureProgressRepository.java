package com.dw.lms.repository;

import com.dw.lms.model.Lecture_progress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureProgressRepository extends JpaRepository<Lecture_progress, Long> {
}
