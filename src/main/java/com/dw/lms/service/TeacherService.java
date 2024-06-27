package com.dw.lms.service;

import com.dw.lms.model.Course_registration;
import com.dw.lms.model.Lecture;
import com.dw.lms.model.Teacher;
import com.dw.lms.model.User;
import com.dw.lms.repository.LectureRepository;
import com.dw.lms.repository.TeacherRepository;
import com.dw.lms.repository.UserRepository;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class TeacherService {
    @Autowired
    TeacherRepository teacherRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    LectureRepository lectureRepository;

    public List<Teacher> getAllTeacher() {
        return teacherRepository.findAll();
    }

    public List<Teacher> getTeacherByLectureId(String lectureId){
        return teacherRepository.findByLecture_LectureId(lectureId);
    }

    public String saveTeacher(Teacher teacher) {
        try {
            // 입력된 teacher 에서 user와 lecture를 가져와 객체를 생성

//            [Teacher 테이블]
//            private User user;
//            private Lecture lecture;
//            private String teacherResume;
//            private LocalDateTime sysDate;
//            private LocalDateTime updDate;

            User user = userRepository.findById(teacher.getUser().getUserId())
                    .orElseThrow(() -> new EntityNotFoundException("User not found"));
            Lecture lecture = lectureRepository.findById(teacher.getLecture().getLectureId())
                    .orElseThrow(() -> new EntityNotFoundException("Lecture not found"));

            teacher.setUser(user);
            teacher.setLecture(lecture);

            System.out.println("getUserId:    " + teacher.getUser().getUserId());
            System.out.println("getLectureId: " + teacher.getLecture().getLectureId());

            // 현재 날짜와 시간을 한 번만 가져옴
            LocalDateTime now = LocalDateTime.now();
            teacher.setSysDate(now); // 현재일시
            teacher.setUpdDate(now); // 현재일시

            // 강사정보를 저장하고 저장된 정보의 userId 반환
            Teacher savedTeacher = teacherRepository.save(teacher);
            return savedTeacher.getUser().getUserId(); // 없으면 Insert, 있으면 Update
        } catch (Exception e) {
            // 예외 발생 시 로그를 남기고 null 반환 (혹은 적절한 예외 처리)
            // 예: log.error("Error saving review", e);
            //throw new ResourceNotFoundException("User", "ID", teacher.getUser().getUserId());
            System.out.println("Error saving Teacher: " + e);
            return "saveTeacher Error!";
        }
    }




}
