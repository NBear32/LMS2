package com.dw.lms.service;

import com.dw.lms.dto.LectureProgressQueryDto;
import com.dw.lms.dto.LectureStatusCountDto;
import com.dw.lms.model.*;
import com.dw.lms.repository.LearningReviewRepository;
import com.dw.lms.repository.LectureProgressRepository;
import com.dw.lms.repository.LectureRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import jdk.swing.interop.SwingInterOpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class LectureProgressService {
    @Autowired
    LectureProgressRepository lectureProgressRepository;

    public List<Lecture_progress> getAllLectureProgress() {
        return lectureProgressRepository.findAll();
    }

    @PersistenceContext
    private EntityManager entityManager;

    public List<Object[]> executeNativeQueryDto(String userId, String lectureId) {
        String sqlQuery = "SELECT A.learning_contents_seq " +
                          "     , B.learning_contents " +
                          "     , A.progress_rate " +
                          "     , A.learning_count " +
                          "     , IFNULL(DATE_FORMAT(A.last_learning_datetime, '%Y-%m-%d %H:%i:%s'),'') as last_learning_datetime " +
                          "     , IFNULL(concat('(', DATE_FORMAT(A.complete_learning_datetime, '%Y-%m-%d %H:%i:%s'), ')'),'') as complete_learning_datetime " +
                          "     , IF(LENGTH(A.learning_time) = 0,'', concat(mid(A.learning_time,1,2),'시', " +
                          "              mid(A.learning_time,3,2),'분', " +
                          "              mid(A.learning_time,5,2),'초')) as learning_time " +
                          "     , concat('(',  " +
                          "              mid(B.learning_playtime,1,2),'시', " +
                          "              mid(B.learning_playtime,3,2),'분', " +
                          "              mid(B.learning_playtime,5,2),'초', ')') as learning_playtime " +
                          "     , learning_pdf_path " +
                          "     , learning_video_path " +
                          "  FROM lecture_progress  A " +
                          "  LEFT JOIN " +
                          "       learning_contents B " +
                          "    ON ( A.lecture_id            = B.lecture_id " +
                          "     AND A.learning_contents_seq = B.learning_contents_seq) " +
                          " WHERE A.user_id    = :userId " +
                          "   AND A.lecture_id = :lectureId " +
                          " ORDER BY A.learning_contents_seq ";

        System.out.println("sqlQuery: " + sqlQuery);

        Query query = entityManager.createNativeQuery(sqlQuery);
        query.setParameter("userId", userId);
        query.setParameter("lectureId", lectureId);

        return query.getResultList(); // Returns a list of Object arrays
    }

    // Native Query 사용2 => Dto 에 담는 예제
    public List<LectureProgressQueryDto> getLectureProgressQueryJPQL(String userId, String lectureId) {

        System.out.println("userId: " + userId);
        System.out.println("lectureId: " + lectureId);

        List<LectureProgressQueryDto> targetDto = new ArrayList<>();
        List<Object[]> results = executeNativeQueryDto(userId, lectureId);

        System.out.println("Object: " + results.get(0).toString());

// [LectureProgressQueryDto]
//        private Long learning_contents_seq;
//        private String learning_contents;
//        private Long progress_rate;
//        private Long learning_count;
//        private String last_learning_datetime;
//        private String complete_learning_datetime;
//        private String learning_time;
//        private String learning_playtime;
//        private String learning_pdf_path;
//        private String learning_video_path;

        // Process the results
        for (Object[] row : results) {
            // Access each column in the row
            System.out.println("Learning_contents_seq: "     +row[0].toString());
            System.out.println("learning_contents: "         +row[1].toString());
            System.out.println("progress_rate: "             +row[2].toString());
            System.out.println("learning_count: "            +row[3].toString());
            System.out.println("last_learning_datetime: "    +row[4].toString());
            System.out.println("complete_learning_datetime: "+row[5].toString());
            System.out.println("learning_time: "             +row[6].toString());
            System.out.println("learning_playtime: "         +row[7].toString());
            System.out.println("learning_pdf_path: "         +row[8].toString());
            System.out.println("learning_video_path: "       +row[9].toString());

            Long    column1Value = Long.valueOf(row[0].toString()); // Assuming column1 is of type String
            String  column2Value = row[1].toString(); // Assuming column2 is of type String
            Long    column3Value = Long.valueOf(row[2].toString()); // Assuming column3 is of type int
            Long    column4Value = Long.valueOf(row[3].toString()); // Assuming column3 is of type int
            String  column5Value = row[4].toString(); // Assuming column2 is of type String
            String  column6Value = row[5].toString(); // Assuming column2 is of type String
            String  column7Value = row[6].toString(); // Assuming column2 is of type String
            String  column8Value = row[7].toString(); // Assuming column2 is of type String
            String  column9Value = row[8].toString(); // Assuming column2 is of type String
            String  column10Value = row[9].toString(); // Assuming column2 is of type String

            System.out.println("column1Value: "+ column1Value);
            System.out.println("column2Value: "+ column2Value);
            System.out.println("column3Value: "+ column3Value);
            System.out.println("column4Value: "+ column4Value);
            System.out.println("column5Value: "+ column5Value);
            System.out.println("column6Value: "+ column6Value);
            System.out.println("column7Value: "+ column7Value);
            System.out.println("column8Value: "+ column8Value);
            System.out.println("column9Value: "+ column9Value);
            System.out.println("column10Value: "+ column10Value);

            LectureProgressQueryDto lectureProgressQueryDto = new LectureProgressQueryDto(column1Value, column2Value, column3Value, column4Value, column5Value, column6Value, column7Value, column8Value, column9Value, column10Value);
            targetDto.add(lectureProgressQueryDto);
            // Do something with the values...
        }

        return targetDto;
    }





}

