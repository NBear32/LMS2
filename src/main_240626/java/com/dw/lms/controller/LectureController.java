package com.dw.lms.controller;

import com.dw.lms.dto.LectureCategoryCountDto;
import com.dw.lms.model.Course_registration;
import com.dw.lms.model.Lecture;
import com.dw.lms.repository.LectureRepository;
import com.dw.lms.service.LectureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LectureController {
    @Autowired
    LectureService lectureService;

    @GetMapping("/lecture")
    public ResponseEntity<List<Lecture>> getAllLecture() {
        return new ResponseEntity<>(lectureService.getAllLecture(),
                HttpStatus.OK);
    }

    @GetMapping("/lecture/categoryCount")
    public ResponseEntity<List<LectureCategoryCountDto>> getLectureCategoryCountJPQL() {
        return new ResponseEntity<>(lectureService.getLectureCategoryCountJPQL(),
                HttpStatus.OK);
    }
}
