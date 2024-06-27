package com.dw.lms.service;

import com.dw.lms.model.Course_registration;
import com.dw.lms.model.Teacher;
import com.dw.lms.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService {
    @Autowired
    TeacherRepository teacherRepository;

    public List<Teacher> getAllTeacher() {
        return teacherRepository.findAll();
    }
}
