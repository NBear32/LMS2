package com.dw.lms.controller;

import com.dw.lms.dto.CourseEnrollCountDto;
import com.dw.lms.dto.CourseLectureCountDto;
import com.dw.lms.dto.LectureProgressQueryDto;
import com.dw.lms.dto.LectureStatusCountDto;
import com.dw.lms.model.Course_registration;
import com.dw.lms.service.CourseRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CourseRegistrationController {
    @Autowired
    CourseRegistrationService courseRegistrationService;

    @DeleteMapping("/course/delCourseRegistration/{userId}/{lectureId}")
    public void deleteCourseRegistration(@PathVariable String userId, @PathVariable String lectureId) {
        courseRegistrationService.deleteCourseRegistration(userId, lectureId);
    }

    @GetMapping("/course/registration")
    public ResponseEntity<List<Course_registration>> getAllRegistration() {
        return new ResponseEntity<>(courseRegistrationService.getAllRegistration(),
                HttpStatus.OK);
    }

    @GetMapping("/course/lectureStatusCount/id/{userId}")
    public ResponseEntity<List<LectureStatusCountDto>> getLectureStatusCountJPQL(@PathVariable String userId) {
        return new ResponseEntity<>(courseRegistrationService.getLectureStatusCountJPQL(userId),
                HttpStatus.OK);
    }

    @PostMapping("/course/saveCourseRegistration")
    public ResponseEntity<String> saveCourseRegistration(@RequestBody Course_registration course_registration) {
        return new ResponseEntity<>(courseRegistrationService.saveCourseRegistration(course_registration),
                HttpStatus.OK);
    }

    @GetMapping("/course/queryCECJPQL/")
    @PreAuthorize("hasAnyRole('ADMIN')") // ADMIN 이외에는 사용 못하게
    public ResponseEntity<List<CourseEnrollCountDto>> getCourseEnrollCountQueryJPQL(){
        String queryData = "%"; // 전체 조회
        return new ResponseEntity<>(courseRegistrationService.getCourseEnrollCountQueryJPQL(queryData), HttpStatus.OK);
    }

    @GetMapping("/course/queryCECJPQL/{lectureName}")
    @PreAuthorize("hasAnyRole('ADMIN')") // ADMIN 이외에는 사용 못하게
    public ResponseEntity<List<CourseEnrollCountDto>> getCourseEnrollCountQueryJPQL(@PathVariable String lectureName){
        String queryData = "%" + lectureName + "%"; // 조건 조회
        return new ResponseEntity<>(courseRegistrationService.getCourseEnrollCountQueryJPQL(queryData), HttpStatus.OK);
    }

    @GetMapping("/course/queryCLCJPQL/")
    @PreAuthorize("hasAnyRole('ADMIN')") // ADMIN 이외에는 사용 못하게
    public ResponseEntity<List<CourseLectureCountDto>> getCourseLectureCountQueryJPQL(){
        String queryData = "%"; // 전체 조회
        return new ResponseEntity<>(courseRegistrationService.getCourseLectureCountQueryJPQL(queryData), HttpStatus.OK);
    }

    @GetMapping("/course/queryCLCJPQL/{userName}")
    @PreAuthorize("hasAnyRole('ADMIN')") // ADMIN 이외에는 사용 못하게
    public ResponseEntity<List<CourseLectureCountDto>> getCourseLectureCountQueryJPQL(@PathVariable String userName){
        String queryData = "%" + userName + "%"; // 조건 조회
        return new ResponseEntity<>(courseRegistrationService.getCourseLectureCountQueryJPQL(queryData), HttpStatus.OK);
    }



}
