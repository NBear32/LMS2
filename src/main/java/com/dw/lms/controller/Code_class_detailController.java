package com.dw.lms.controller;

import com.dw.lms.model.Code_class;
import com.dw.lms.model.Code_class_detail;
import com.dw.lms.model.Teacher;
import com.dw.lms.repository.Code_class_detailRepository;
import com.dw.lms.service.Code_class_detailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public class Code_class_detailController {
    @Autowired
    Code_class_detailService code_class_detailService;

    @GetMapping("/code/codeClassDetail")
    public ResponseEntity<List<Code_class_detail>> getAllCodeClass() {
        return new ResponseEntity<>(code_class_detailService.getAllCodeClassDetail(),
                HttpStatus.OK);
    }

    @PostMapping("/code/saveCodeClassDetail")
    public ResponseEntity<String> saveCodeClassDetail(@RequestBody Code_class_detail code_class_detail) {
        return new ResponseEntity<>(code_class_detailService.saveCodeClassDetail(code_class_detail),
                HttpStatus.OK);
    }



}
