package com.dw.lms.service;

import com.dw.lms.model.User;
import com.dw.lms.model.Lecture;
import com.dw.lms.model.Course_registration;
import com.dw.lms.model.Learning_review;
import com.dw.lms.model.CK.Course_registration_CK;

import com.dw.lms.repository.CourseRegistrationRepository;
import com.dw.lms.repository.LearningReviewRepository;
import com.dw.lms.repository.LectureRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class LearningReviewService {
    @Autowired
    LearningReviewRepository learningReviewRepository;
    @Autowired
    LectureRepository lectureRepository;

    @Autowired
    CourseRegistrationService courseRegistrationService;

    public List<Learning_review> getAllReview() {
        return learningReviewRepository.findAll();
    }

    public List<Learning_review> getReviewByLectureId(String lectureId){
        List<Learning_review> learningReviews = learningReviewRepository.findAll();
        List<Learning_review> learningReviewList = new ArrayList<>();
        for (int i = 0; i < learningReviews.size(); i++) {
            if (learningReviews.get(i).getCourse_registration().getLecture().getLectureId().equals(lectureId)){
                learningReviewList.add(learningReviews.get(i));
            }
        }
        return learningReviewList;
    }

    public List<Learning_review> getReviewByLectureIdUserId(String lectureId, String userId){
        List<Learning_review> learningReviews = learningReviewRepository.findAll();
        List<Learning_review> learningReviewLectureList = new ArrayList<>();
        List<Learning_review> learningReviewList = new ArrayList<>();
        // lectureId 조회
        for (int i = 0; i < learningReviews.size(); i++) {
            if (learningReviews.get(i).getCourse_registration().getLecture().getLectureId().equals(lectureId)){
                learningReviewLectureList.add(learningReviews.get(i));
            }
        }
        // userId 조회
        for (int i = 0; i < learningReviewLectureList.size(); i++) {
            if(learningReviewLectureList.get(i).getCourse_registration().getUser().getUserId().equals(userId))
                learningReviewList.add(learningReviewLectureList.get(i));
        }
        return learningReviewList;
    }

    public String saveReview(Learning_review learning_review) {
        try {
            // 입력된 리뷰의 course_registration에서 user와 lecture를 가져와 객체를 생성
            User inputUser = new User();
            inputUser.setUserId(learning_review.getCourse_registration().getUser().getUserId());

            Lecture inputLecture = new Lecture();
            inputLecture.setLectureId(learning_review.getCourse_registration().getLecture().getLectureId());

            // 주어진 user와 lecture로 course_registration을 찾음
            Course_registration entityCR = courseRegistrationService.findCourseRegistrationByCompositeKey(inputUser, inputLecture);

            // 리뷰의 course_registration 설정
            learning_review.setCourse_registration(entityCR);

            // 현재 날짜와 시간을 한 번만 가져옴
            LocalDateTime now = LocalDateTime.now();
            learning_review.setLearningReviewDate(now.toLocalDate()); // 현재일자
            learning_review.setSysDate(now); // 현재일시
            learning_review.setUpdDate(now); // 현재일시

            // 리뷰를 저장하고 저장된 리뷰의 userId 반환
            Learning_review savedReview = learningReviewRepository.save(learning_review);
            return savedReview.getCourse_registration().getUser().getUserId(); // 없으면 Insert, 있으면 Update
        } catch (Exception e) {
            // 예외 발생 시 로그를 남기고 null 반환 (혹은 적절한 예외 처리)
            // 예: log.error("Error saving review", e);
            //throw new ResourceNotFoundException("User", "ID", learning_review.getCourse_registration().getUser().getUserId());
            System.out.println("Error saving review: " + e);
            return "saveReview Error!";
        }
    }

}

