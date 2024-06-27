package com.dw.lms.service;

import com.dw.lms.model.Learning_contents;
import com.dw.lms.model.Lecture;
import com.dw.lms.model.Teacher;
import com.dw.lms.model.User;
import com.dw.lms.repository.LearningContentsRepository;
import com.dw.lms.repository.LectureRepository;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class LearningContentsService {
    @Autowired
    LearningContentsRepository learningContentsRepository;

    @Autowired
    LectureRepository lectureRepository;

    public List<Learning_contents> getAllContents() {
        return learningContentsRepository.findAll();
    }
    
    public List<Learning_contents> getContentsByLectureId(String lectureId){
        // sort는 JPA에서 제공하는 정렬 옵션
        // sort = 오름차순 or 내림차순 DESC -> 내림차순 ASC -> 오름차순
        Sort sort = Sort.by(Sort.Direction.ASC, "learningContentsSeq");
        return learningContentsRepository.findByLecture_LectureId(lectureId, sort);
    }

    public List<Learning_contents> getContentByLectureIdBySeq(String lectureId, Long seq){
        List<Learning_contents> learningContents = learningContentsRepository.findAll();
        List<Learning_contents> learning_contentsLecture = new ArrayList<>();
        List<Learning_contents> learning_contentsList = new ArrayList<>();

        for (int i = 0; i < learningContents.size(); i++) {
            if (learningContents.get(i).getLecture().getLectureId().equals(lectureId)){
                learning_contentsLecture.add(learningContents.get(i));
            }
        }
        for (int i = 0; i < learning_contentsLecture.size(); i++) {
            if (learning_contentsLecture.get(i).getLearningContentsSeq().equals(seq)){
                learning_contentsList.add(learning_contentsLecture.get(i));
            }
        }
        return learning_contentsList;
    }

    public String saveLearningContents(Learning_contents learning_contents) {
        try {
            // 입력된 강의목차 에서 lecture를 가져와 객체를 생성

//            [Learning_contents 테이블]
//            private Lecture lecture;
//            private Long learningContentsSeq;
//            private String learningContents;
//            private String learningPlaytime;
//            private LocalDateTime sysDate;
//            private LocalDateTime updDate;

            Lecture lecture = lectureRepository.findById(learning_contents.getLecture().getLectureId())
                    .orElseThrow(() -> new EntityNotFoundException("Lecture not found"));

            learning_contents.setLecture(lecture);

            System.out.println("getLectureId: " + learning_contents.getLecture().getLectureId());
            System.out.println("getLearningContentsSeq: " + learning_contents.getLearningContentsSeq().toString());
            System.out.println("getLearningContents: " + learning_contents.getLearningContents());
            System.out.println("getLearningPlaytime: " + learning_contents.getLearningPlaytime());

            // 현재 날짜와 시간을 한 번만 가져옴
            LocalDateTime now = LocalDateTime.now();
            learning_contents.setSysDate(now); // 현재일시
            learning_contents.setUpdDate(now); // 현재일시

            // 강의목차 정보를 저장하고 저장된 정보의 lectureId 반환
            Learning_contents savedLearningContents = learningContentsRepository.save(learning_contents);
            return savedLearningContents.getLecture().getLectureId(); // 없으면 Insert, 있으면 Update
        } catch (Exception e) {
            // 예외 발생 시 로그를 남기고 null 반환 (혹은 적절한 예외 처리)
            // 예: log.error("Error saving review", e);
            //throw new ResourceNotFoundException("User", "ID", teacher.getUser().getUserId());
            System.out.println("Error saving LearningContents: " + e);
            return "saveLearningContents Error!";
        }
    }



}
