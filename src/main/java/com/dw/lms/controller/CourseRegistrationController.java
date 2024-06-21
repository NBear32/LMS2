package com.dw.lms.controller;

import com.dw.lms.dto.LectureStatusCountDto;
import com.dw.lms.model.Course_registration;
import com.dw.lms.service.CourseRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CourseRegistrationController {
    @Autowired
    CourseRegistrationService CourseRegistrationService;

    @DeleteMapping("/course/delCourseRegistration/{userId}/{lectureId}")
    public void deleteCourseRegistration(@PathVariable String userId, @PathVariable String lectureId) {
        CourseRegistrationService.deleteCourseRegistration(userId, lectureId);
    }

    @GetMapping("/course/registration")
    public ResponseEntity<List<Course_registration>> getAllRegistration() {
        return new ResponseEntity<>(CourseRegistrationService.getAllRegistration(),
                HttpStatus.OK);
    }

    @GetMapping("/course/lectureStatusCount/id/{userId}")
    public ResponseEntity<List<LectureStatusCountDto>> getLectureStatusCountJPQL(@PathVariable String userId) {
        return new ResponseEntity<>(CourseRegistrationService.getLectureStatusCountJPQL(userId),
                HttpStatus.OK);
    }

    @PostMapping("/course/saveCourseRegistration")
    public ResponseEntity<String> saveCourseRegistration(@RequestBody Course_registration course_registration) {
        return new ResponseEntity<>(CourseRegistrationService.saveCourseRegistration(course_registration),
                HttpStatus.OK);
    }

}
