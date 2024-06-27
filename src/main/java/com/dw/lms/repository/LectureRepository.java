package com.dw.lms.repository;

import com.dw.lms.model.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LectureRepository extends JpaRepository<Lecture, String> {

    @Query("select a from Lecture a order by a.lectureId desc")
    public List<Lecture> getAllLecture();
}
