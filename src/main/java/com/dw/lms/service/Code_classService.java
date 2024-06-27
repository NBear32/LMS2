package com.dw.lms.service;

import com.dw.lms.model.Code_class;
import com.dw.lms.repository.Code_classRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class Code_classService {

    @Autowired
    Code_classRepository code_classRepository;

    public List<Code_class> getAllCode_class() {
        return code_classRepository.findAll();
    }

}
