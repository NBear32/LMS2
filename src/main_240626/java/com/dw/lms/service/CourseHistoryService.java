package com.dw.lms.service;

import com.dw.lms.model.*;
import com.dw.lms.model.CK.Course_history_CK;
import com.dw.lms.model.CK.Course_registration_CK;
import com.dw.lms.repository.CourseHistoryRepository;
import com.dw.lms.repository.CourseRegistrationRepository;
import com.dw.lms.repository.LectureRepository;
import com.dw.lms.repository.UserRepository;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class CourseHistoryService {
    @Autowired
    CourseHistoryRepository courseHistoryRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    LectureRepository lectureRepository;

    @Autowired
    CourseRegistrationService courseRegistrationService;

    public List<Course_history> getAllHistory() {
        return courseHistoryRepository.findAll();
    }

    public String saveCourseHistory(Course_history course_history) {
        try {

//            [Course_registration 테이블]
//            @JoinColumns({
//                    @JoinColumn(name="user_id", referencedColumnName = "user_id"),
//                    @JoinColumn(name="lecture_id", referencedColumnName = "lecture_id")
//            })
//            private Course_registration course_registration;
//            private Long courseHistorySeq;
//            private String connectionIp;
//            private LocalDate connectionStartDate;
//            private LocalDate connectionEndDate;
//            private LocalDateTime sysDate;
//            private LocalDateTime updDate;

            // 입력된 Course_history 에서 user와 lecture를 가져와 객체를 생성
            User inputUser = userRepository.findById(course_history.getCourse_registration().getUser().getUserId())
                    .orElseThrow(() -> new EntityNotFoundException("User not found"));
            Lecture inputLecture = lectureRepository.findById(course_history.getCourse_registration().getLecture().getLectureId())
                    .orElseThrow(() -> new EntityNotFoundException("Lecture not found"));

            // 주어진 user와 lecture로 course_registration을 찾음
            Course_registration entityCR = courseRegistrationService.findCourseRegistrationByCompositeKey(inputUser, inputLecture);

            // 리뷰의 course_registration 설정
            course_history.setCourse_registration(entityCR);
            Long course_history_seq = Long.valueOf(0);
            course_history.setCourseHistorySeq(course_history_seq);

            System.out.println("connectionIp: " + course_history.getConnectionIp());

            // 현재 날짜와 시간을 한 번만 가져옴
            LocalDateTime now = LocalDateTime.now();
            course_history.setConnectionStartDate(now); // 현재일시
            course_history.setConnectionEndDate(now);   // 현재일시
            course_history.setSysDate(now); // 현재일시
            course_history.setUpdDate(now); // 현재일시

            // 강의 접속 이력를 저장하고 저장된 데이터의 userId 반환
            Course_history savedCourseHistory = courseHistoryRepository.save(course_history);
            return savedCourseHistory.getCourse_registration().getUser().getUserId(); // 없으면 Insert, 있으면 Update
        } catch (Exception e) {
            // 예외 발생 시 로그를 남기고 null 반환 (혹은 적절한 예외 처리)
            // 예: log.error("Error saving review", e);
            //throw new ResourceNotFoundException("User", "ID", learning_review.getCourse_registration().getUser().getUserId());
            System.out.println("Error saving CourseHistory: " + e);
            return "saveCourseHistory Error!";
        }
    }



}