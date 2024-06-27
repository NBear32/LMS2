package com.dw.lms.service;

import com.dw.lms.dto.LectureCategoryCountDto;
import com.dw.lms.model.Course_registration;
import com.dw.lms.model.Lecture;
import com.dw.lms.repository.LectureRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LectureService {
    @Autowired
    LectureRepository lectureRepository;

    public List<Lecture> getAllLecture() {
    		// findAll() ���� ��ȸ ������ ��������, repository ���� Query �� ����Ͽ� �������� ��ȸ�� ����
        // List<Lecture> lecture = lectureRepository.findAll();
        return lectureRepository.getAllLecture();
    }

    @PersistenceContext
    private EntityManager entityManager;

    public List<Object[]> executeNativeQueryDto() {
        String sqlQuery = "select '00' as category_id " +
                          "     , '전체과정' as category_name " +
                          "     , count(*) as category_count " +
                          "  from lecture a " +
                          "union all " +
                          "select a.category_id " +
                          "     , b.category_name " +
                          "     , count(*) as category_count " +
                          "  from category b " +
                          "     , lecture  a " +
                          " where a.category_id = b.category_id " +
                          " group by " +
                          "       a.category_id " +
                          "     , b.category_name " +
                          " order by 1 ";
        Query query = entityManager.createNativeQuery(sqlQuery);

        return query.getResultList(); // Returns a list of Object arrays
    }

    // Native Query 사용2 => Dto 에 담는 예제
    public List<LectureCategoryCountDto> getLectureCategoryCountJPQL() {

        List<LectureCategoryCountDto> targetDto = new ArrayList<>();
        List<Object[]> results = executeNativeQueryDto();

// [LectureCategoryCountDto]
//        private String CategoryId;
//        private String CategoryName;
//        private Long CategoryCount;

        // Process the results
        for (Object[] row : results) {
            // Access each column in the row
            System.out.println("CategoryId:    "+row[0].toString());
            System.out.println("CategoryName:  "+row[1].toString());
            System.out.println("CategoryCount: "+row[2].toString());

            String  column1Value = row[0].toString(); // Assuming column1 is of type String
            String  column2Value = row[1].toString(); // Assuming column2 is of type String
            Long    column3Value = Long.valueOf(row[2].toString()); // Assuming column3 is of type int

            System.out.println("column1Value: "+ column1Value);
            System.out.println("column2Value: "+ column2Value);
            System.out.println("column3Value: "+ column3Value);

            LectureCategoryCountDto lectureCategoryCountDto = new LectureCategoryCountDto(column1Value, column2Value, column3Value);
            targetDto.add(lectureCategoryCountDto);
            // Do something with the values...
        }

        return targetDto;
    }







}
