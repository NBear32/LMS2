package com.dw.lms.controller;

import com.dw.lms.model.Code_class;
import com.dw.lms.service.Code_classService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class Code_classController {
    @Autowired
    Code_classService code_classService;

    @GetMapping("/lms/code_class")
    public ResponseEntity<List<Code_class>> getAllCode_class() {
        return new ResponseEntity<>(code_classService.getAllCode_class(),
                HttpStatus.OK);
    }

}
