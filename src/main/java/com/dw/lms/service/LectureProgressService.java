package com.dw.lms.service;

import com.dw.lms.dto.LectureProgressDto;
import com.dw.lms.model.Lecture_progress;
import com.dw.lms.repository.LectureProgressRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LectureProgressService {
    @Autowired
    LectureProgressRepository lectureProgressRepository;

    public List<Lecture_progress> getAllLectureProgress() {
        return lectureProgressRepository.findAll();
    }

    @PersistenceContext
    private EntityManager entityManager;
    public List<Object[]> progressQueryDto( String userId,String lectureId){
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
        Query query = entityManager.createNativeQuery(sqlQuery);
        query.setParameter("userId", userId);
        query.setParameter("lectureId", lectureId);


        return query.getResultList();
    }

    public List<LectureProgressDto> getLectureProgressDetails( String userId, String lectureId){
        List<LectureProgressDto> lectureProgress = new ArrayList<>();
        List<Object[]> results = progressQueryDto(userId,lectureId);
        for (Object[] row: results){
            Long column1Value = Long.valueOf(row[0].toString()); // learning_contents_seq
            String column2Value = row[1].toString(); // learning_contents
            Long column3Value = Long.valueOf(row[2].toString()); // progress_rate
            Long column4Value = Long.valueOf(row[3].toString()); // learning_count
            String column5Value = row[4].toString(); // last_learning_datetime
            String column6Value = row[5].toString(); // complete_learning_datetime
            String column7Value = row[6].toString(); // learning_time
            String column8Value = row[7].toString(); // learning_playtime
            String column9Value = row[8].toString(); // learning_pdf_path
            String column10Value = row[9].toString(); // learning_video_path

//            private Long learning_contents_seq;
//            private String learning_contents;
//            private Long progress_rate;
//            private Long learning_count;
//            private String last_learning_datetime;
//            private String complete_learning_datetime;
//            private String learning_time;
//            private String learning_playtime;
//            private String learning_pdf_path;
//            private String learning_video_path;

            LectureProgressDto lectureProgressDto = new LectureProgressDto(column1Value, column2Value, column3Value, column4Value, column5Value,
                    column6Value, column7Value, column8Value, column9Value, column10Value);
            lectureProgress.add(lectureProgressDto);

        }
        return lectureProgress;
    }

}
