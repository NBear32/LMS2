package com.dw.lms.service;

import com.dw.lms.model.Course_registration;
import com.dw.lms.model.Learning_contents;
import com.dw.lms.repository.LearningContentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LearningContentsService {
    @Autowired
    LearningContentsRepository learningContentsRepository;

    public List<Learning_contents> getAllContents() {
        return learningContentsRepository.findAll();
    }
}
