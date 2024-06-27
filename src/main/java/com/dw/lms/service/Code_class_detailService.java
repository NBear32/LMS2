package com.dw.lms.service;

import com.dw.lms.model.*;
import com.dw.lms.repository.Code_classRepository;
import com.dw.lms.repository.Code_class_detailRepository;
import jakarta.persistence.Column;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Id;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class Code_class_detailService {
    @Autowired
    Code_class_detailRepository code_class_detailRepository;

    @Autowired
    Code_classRepository code_classRepository;

    public List<Code_class_detail> getAllCodeClassDetail() {
        return code_class_detailRepository.findAll();
    }

    public String saveCodeClassDetail(Code_class_detail code_class_detail) {
        try {
            // 입력된 code_class_detail 에서 code_class 를 가져와 객체를 생성

//            [Code_class_detail 테이블]
//            private Code_class codeClass;
//            private String code;
//            private String codeName;
//            private Long sortSeq;
//            private LocalDateTime sysDate;
//            private LocalDateTime updDate;

            Code_class code_class = code_classRepository.findById(code_class_detail.getCodeClass().getCodeClass())
                    .orElseThrow(() -> new EntityNotFoundException("User not found"));

            code_class_detail.setCodeClass(code_class);

            System.out.println("getCodeClass: " + code_class_detail.getCodeClass().getCodeClass());
            System.out.println("getCode     : " + code_class_detail.getCode());
            System.out.println("getCodeName : " + code_class_detail.getCodeName());
            System.out.println("getSortSeq  : " + code_class_detail.getSortSeq().toString());

            // 현재 날짜와 시간을 한 번만 가져옴
            LocalDateTime now = LocalDateTime.now();
            code_class_detail.setSysDate(now); // 현재일시
            code_class_detail.setUpdDate(now); // 현재일시

            // 코드 디테일 정보를 저장하고 저장된 정보의 코드 클래스:코드 를 반환
            Code_class_detail savedCode_class_detail = code_class_detailRepository.save(code_class_detail);
            return savedCode_class_detail.getCodeClass().getCodeClass() + ":" + savedCode_class_detail.getCode(); // 없으면 Insert, 있으면 Update
        } catch (Exception e) {
            // 예외 발생 시 로그를 남기고 null 반환 (혹은 적절한 예외 처리)
            // 예: log.error("Error saving review", e);
            //throw new ResourceNotFoundException("User", "ID", teacher.getUser().getUserId());
            System.out.println("Error saving CodeClassDetail: " + e);
            return "saveCodeClassDetail Error!";
        }
    }



}
