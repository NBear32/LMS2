package com.dw.lms.controller;

import com.dw.lms.model.Course_registration;
import com.dw.lms.model.Teacher;
import com.dw.lms.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

public class TeacherController {
    @Autowired
    TeacherService teacherService;

    @GetMapping("/teacher")
    public ResponseEntity<List<Teacher>> getAllTeacher() {
        return new ResponseEntity<>(teacherService.getAllTeacher(),
                HttpStatus.OK);
    }

    @GetMapping("/teacher/{lectureId}")
    public ResponseEntity<List<Teacher>>getTeacherByLectureId(@PathVariable String lectureId){
        return new ResponseEntity<>(teacherService.getTeacherByLectureId(lectureId),HttpStatus.OK);
    }

    @PostMapping("/teacher/saveTeacher")
    public ResponseEntity<String> saveCourseRegistration(@RequestBody Teacher teacher) {
        return new ResponseEntity<>(teacherService.saveTeacher(teacher),
                HttpStatus.OK);
    }



}
