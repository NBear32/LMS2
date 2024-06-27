package com.dw.lms.repository;

import com.dw.lms.model.Course_history;
import com.dw.lms.model.CK.Course_history_CK;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseHistoryRepository extends JpaRepository<Course_history, Course_history_CK> {
}
