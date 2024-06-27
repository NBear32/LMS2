package com.dw.lms.service;

import com.dw.lms.dto.LectureCategoryCountDto;
import com.dw.lms.exception.ResourceNotFoundException;
import com.dw.lms.model.Lecture;
import com.dw.lms.repository.LectureRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class LectureService {
    @Autowired
    LectureRepository lectureRepository;

    public List<Lecture> getAllLecture() {
        // List<Lecture> lecture = lectureRepository.findAll();
        return lectureRepository.getAllLecture();
    }

    // search가 포함된 데이터만 조회
    public List<Lecture> searchLecturesByKeyword(String search) {
        List<Lecture> allLectures = lectureRepository.findAll();
        List<Lecture> filteredLectures = new ArrayList<>();

        for (Lecture lecture : allLectures) {
            if (lecture.getLectureName().contains(search)) {
                filteredLectures.add(lecture);
            }
        }
        return filteredLectures;
    }

    // select 박스만 검색
    public List<Lecture> getLectureByCategory(String category) {
        List<Lecture> allLectures = lectureRepository.findAll();
        List<Lecture> lecturesInCategory = new ArrayList<>();

        if ("00".equals(category)) {
            return allLectures;
        }

        for (Lecture lecture : allLectures) {
            if (lecture.getCategory().getCategoryId().equals(category)) {
                lecturesInCategory.add(lecture);
            }
        }

        return lecturesInCategory;
    }

    // select박스 search 둘 다 조회
    public List<Lecture> searchLectureByKeywordAndCategory(String search, String category) {
        List<Lecture> allLectures = lectureRepository.findAll();
        List<Lecture> foundLectures = new ArrayList<>();
        // 만약 search가 없으면 getLectureByCategory 실행(category만 검색)
        if (search.isEmpty()){
            return getLectureByCategory(category);
        }
        // 만약 category가 전체이거나, 없고, search가 빈칸이 아니면 searchLecturesByKeyword 실행 (search로만 검색)
        if ("00".equals(category) || category.isEmpty() && !search.isEmpty()){
            return searchLecturesByKeyword(search);
        }

        // 만약 category가 전체이거나, 빈칸이고, search가 없으면 getAllLecture 실행 (전체검색)
        if ("00".equals(category)|| category.isEmpty() && search.isEmpty()){
            return getAllLecture();
        }

        // search, category가 있으면 둘 다 찾아서 검색
        for (Lecture lecture : allLectures) {
            if (lecture.getLectureName().contains(search) ||
                    lecture.getCategory().getCategoryId().equals(search)) {
                if (lecture.getCategory().getCategoryId().equals(category)) {
                    foundLectures.add(lecture);
                }
            }
        }

        return foundLectures;
    }

    // main.html에서 상단 무료, 유료, 추천, 신규, 전체 누를때마다 조건에 맞는 data 조회됨
    public List<Lecture> getCategoryLecture(String keyword) {
        List<Lecture> allLecture = lectureRepository.findAll();
        List<Lecture> lectureList = new ArrayList<>();

        LocalDateTime now = LocalDateTime.now();

        if ("무료".equals(keyword)) {
            for (Lecture lecture : allLecture) {
                if (lecture.getEducationPrice() == 0) {
                    lectureList.add(lecture);
                }
            }
        } else if ("유료".equals(keyword)) {
            for (Lecture lecture : allLecture) {
                if (lecture.getEducationPrice() > 0) {
                    lectureList.add(lecture);
                }
            }
        } else if ("추천".equals(keyword)) {
            for (Lecture lecture : allLecture) {
                if ("Y".equals(lecture.getInterestedCheck())) {
                    lectureList.add(lecture);
                }
            }
        } else if ("신규".equals(keyword)) {
            for (Lecture lecture : allLecture) {
                LocalDateTime lectureDate = lecture.getSysDate();
                if (lectureDate.isAfter(now.minusDays(10))) {
                    lectureList.add(lecture);
                }
            }
        } else {
            return getAllLecture();
        }

        return lectureList;
    }

    public Lecture getLecture(String lectureId){
        Optional<Lecture> lectureOptional=lectureRepository.findById(lectureId);
        if (lectureOptional.isPresent()){
            return lectureOptional.get();
        }else {
            throw new ResourceNotFoundException("Lecture","lectureId",lectureId);
        }
    }

    public List<Lecture> saveLectureList(List<Lecture> lectureList) {
        List<Lecture> savedLectureList = lectureList.stream()
                .map(lecture -> {
                    // lecture.setCreationTime(LocalDateTime.now()); // 예시로 생성 시간 설정
                    return lectureRepository.save(lecture);
                })
                .collect(Collectors.toList());
        return savedLectureList;
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
