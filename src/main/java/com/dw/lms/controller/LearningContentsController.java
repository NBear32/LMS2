package com.dw.lms.controller;

import com.dw.lms.model.Learning_contents;
import com.dw.lms.service.LearningContentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LearningContentsController {
    @Autowired
    LearningContentsService learningContentsService;

    @GetMapping("/learning/contents")
    public ResponseEntity<List<Learning_contents>> getAllContents() {
        return new ResponseEntity<>(learningContentsService.getAllContents(),
                HttpStatus.OK);
    }

    @GetMapping("/learning/contents/{lectureId}")
    public ResponseEntity<List<Learning_contents>>getContentsByLectureId(@PathVariable String lectureId){
        return new ResponseEntity<>(learningContentsService.getContentsByLectureId(lectureId),HttpStatus.OK);
    }

    @GetMapping("/learning/contents/{lectureId}/{seq}")
    public ResponseEntity<List<Learning_contents>>getContentByLectureIdBySeq(@PathVariable String lectureId, @PathVariable Long seq){
        return new ResponseEntity<>(learningContentsService.getContentByLectureIdBySeq(lectureId, seq), HttpStatus.OK);
    }



}
