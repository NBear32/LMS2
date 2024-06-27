package com.dw.lms.controller;

import com.dw.lms.model.Course_history;
import com.dw.lms.service.CourseHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CourseHistoryController {
    @Autowired
    CourseHistoryService courseHistoryService;

    @GetMapping("/course/history")
    public ResponseEntity<List<Course_history>> getAllHistory() {
        return new ResponseEntity<>(courseHistoryService.getAllHistory(),
                HttpStatus.OK);
    }
}
