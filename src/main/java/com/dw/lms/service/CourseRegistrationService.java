package com.dw.lms.service;

import com.dw.lms.dto.CourseEnrollCountDto;
import com.dw.lms.dto.CourseLectureCountDto;
import com.dw.lms.dto.LectureStatusCountDto;
import com.dw.lms.model.CK.Course_registration_CK;
import com.dw.lms.model.Course_registration;
import com.dw.lms.model.Lecture;
import com.dw.lms.model.User;
import com.dw.lms.repository.CourseRegistrationRepository;
import com.dw.lms.repository.LectureRepository;
import com.dw.lms.repository.UserRepository;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CourseRegistrationService {
    @Autowired
    CourseRegistrationRepository courseRegistrationRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    LectureRepository lectureRepository;

    public void deleteCourseRegistration(String userId, String lectureId) {
        User inputUser = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        Lecture inputLecture = lectureRepository.findById(lectureId)
                .orElseThrow(() -> new EntityNotFoundException("Lecture not found"));

        Course_registration_CK compositeKey = new Course_registration_CK(inputUser, inputLecture);
        courseRegistrationRepository.deleteById(compositeKey);
    }

    public List<Course_registration> getAllRegistration() {
        return courseRegistrationRepository.findAll();
    }

    // Course_registration_CK 키 가져오는 부분
    public Course_registration findCourseRegistrationByCompositeKey(User user, Lecture lecture) {
        Course_registration_CK course_registration_CK = new Course_registration_CK(user, lecture);
        Course_registration entityCR = courseRegistrationRepository.findById(course_registration_CK)
                .orElseThrow(() -> new EntityNotFoundException("course_registration_CK not found"));
        return entityCR;
    }

    @PersistenceContext
    private EntityManager entityManager;

    // 수강목차의 마지막 횟차 가져오는 SQL
    public List<Object> executeNativeQueryFLCS(String lectureId) {
        String sqlQuery = "select max(learning_contents_seq) from learning_contents where lecture_id = :lectureId";
        Query query = entityManager.createNativeQuery(sqlQuery);
        query.setParameter("lectureId", lectureId);
        return query.getResultList(); // Returns a list of Objects
    }

    // 수강목차의 마지막 횟차 가져오는 SQL 실행 후 값을 가져오는 부분
    public Long getFinalLectureContentsSeqJPQL(String lectureId) {
        List<Object> results = executeNativeQueryFLCS(lectureId);

        Long column1Value = 0L; // 초기값을 Long 타입으로 설정

        System.out.println("getFinalLectureContentsSeqJPQL Start!!!");

        if (!results.isEmpty()) {
            Object result = results.get(0);
            if (result != null) {
                column1Value = ((Number) result).longValue(); // Object를 Number로 캐스팅하여 Long 값으로 변환
                System.out.println("column1Value: " + column1Value);
            }
        }

        System.out.println("getFinalLectureContentsSeqJPQL End!!!");

        return column1Value;
    }

    // 수강신청을 하는 부분
    public String saveCourseRegistration(Course_registration course_registration) {
        try {
            // 입력된 course_registration 에서 user와 lecture를 가져와 객체를 생성

//            [Course_registration 테이블]
//            private User user;
//            private Lecture lecture;
//            private LocalDate courseRegistrationDate;
//            private Long finalLectureContentsSeq;
//            private Long progressLectureContentsSeq;
//            private String lectureStatus;
//            private String lectureCompletedCheck;
//            private LocalDateTime sysDate;
//            private LocalDateTime updDate;

            User user = userRepository.findById(course_registration.getUser().getUserId())
                    .orElseThrow(() -> new EntityNotFoundException("User not found"));
            Lecture lecture = lectureRepository.findById(course_registration.getLecture().getLectureId())
                    .orElseThrow(() -> new EntityNotFoundException("Lecture not found"));

            course_registration.setUser(user);
            course_registration.setLecture(lecture);

            System.out.println("getLectureId:" + course_registration.getLecture().getLectureId());

            Long finalSeq = getFinalLectureContentsSeqJPQL(course_registration.getLecture().getLectureId());
            course_registration.setFinalLectureContentsSeq(finalSeq);

            System.out.println("getFinalLectureContentsSeqJPQL:" + finalSeq);

            Long contentSeq = Long.valueOf(0);
            course_registration.setProgressLectureContentsSeq(contentSeq);

            course_registration.setLectureStatus("I");
            course_registration.setLectureCompletedCheck("N");

            // 현재 날짜와 시간을 한 번만 가져옴
            LocalDateTime now = LocalDateTime.now();
            course_registration.setCourseRegistrationDate(now.toLocalDate()); // 현재일자
            course_registration.setSysDate(now); // 현재일시
            course_registration.setUpdDate(now); // 현재일시

            // 수강신청을 저장하고 저장된 정보의 userId 반환
            Course_registration savedCourseRegistration = courseRegistrationRepository.save(course_registration);
            return savedCourseRegistration.getUser().getUserId(); // 없으면 Insert, 있으면 Update
        } catch (Exception e) {
            // 예외 발생 시 로그를 남기고 null 반환 (혹은 적절한 예외 처리)
            // 예: log.error("Error saving review", e);
            //throw new ResourceNotFoundException("User", "ID", learning_review.getCourse_registration().getUser().getUserId());
            System.out.println("Error saving CourseRegistration: " + e);
            return "saveCourseRegistration Error!";
        }
    }

    public List<Object[]> executeNativeQueryDto3(String userName) {
        String sqlQuery = "SELECT B.user_id " +
                          "     , B.user_name " +
                          "     , B.email " +
                          "     , B.act_yn " +
                          "     , count(*) as course_registration_cnt " +
                          "  FROM user  B " +
                          "     , course_registration A " +
                          " WHERE A.user_id  = B.user_id " +
                          "   AND B.user_name like :userName " +
                          " GROUP BY " +
                          "       B.user_id " +
                          "     , B.user_name " +
                          "     , B.email " +
                          "     , B.act_yn " +
                          " UNION ALL " +
                          "SELECT B.user_id " +
                          "     , B.user_name " +
                          "     , B.email " +
                          "     , B.act_yn " +
                          "     , 0 as course_registration_cnt " +
                          "  FROM user  B " +
                          " WHERE B.user_name like :userName " +
                          "   AND NOT EXISTS ( SELECT * " +
                          "                      FROM course_registration A " +
                          "                     WHERE A.user_id = B.user_id ) " +
                          " ORDER BY 5 desc, 1 ";
        Query query = entityManager.createNativeQuery(sqlQuery);
        query.setParameter("userName", userName); // 전체는 %, 조건이 있으면 %조건%

        return query.getResultList(); // Returns a list of Object arrays
    }

    public List<CourseLectureCountDto> getCourseLectureCountQueryJPQL(String userName) {

        List<CourseLectureCountDto> targetDto = new ArrayList<>();
        List<Object[]> results = executeNativeQueryDto3(userName);

// [CourseEnrollCountDto]
//        private String lectureId;
//        private String lectureName;
//        private String lectureStartDate;
//        private String lectureEndDate;
//        private String categoryName;
//        private Long courseEnrollCount;

        // Process the results
        for (Object[] row : results) {
            // Access each column in the row
            System.out.println("userId: "            +row[0].toString());
            System.out.println("userName: "          +row[1].toString());
            System.out.println("userEmail: "         +row[2].toString());
            System.out.println("actYn: "             +row[3].toString());
            System.out.println("CauseLectureCount: " +row[4].toString());

            String  column1Value = row[0].toString(); // Assuming column1 is of type String
            String  column2Value = row[1].toString(); // Assuming column2 is of type String
            String  column3Value = row[2].toString(); // Assuming column3 is of type String
            String  column4Value = row[3].toString(); // Assuming column4 is of type String
            Long    column5Value = Long.valueOf(row[4].toString()); // Assuming column6 is of type int

            System.out.println("column1Value: "+ column1Value);
            System.out.println("column2Value: "+ column2Value);
            System.out.println("column3Value: "+ column3Value);
            System.out.println("column4Value: "+ column4Value);
            System.out.println("column5Value: "+ column5Value);

            CourseLectureCountDto courseLectureCountDto = new CourseLectureCountDto(column1Value, column2Value, column3Value, column4Value, column5Value);
            targetDto.add(courseLectureCountDto);
            // Do something with the values...
        }

        return targetDto;
    }

    public List<Object[]> executeNativeQueryDto2(String lectureName) {
        String sqlQuery = "SELECT B.lecture_id " +
                          "     , B.lecture_name " +
                          "     , B.education_period_start_date " +
                          "     , B.education_period_end_date " +
                          "     , C.category_name " +
                          "     , count(*) as course_registration_cnt " +
                          "  FROM category C " +
                          "     , lecture  B " +
                          "     , course_registration A " +
                          " WHERE A.lecture_id  = B.lecture_id " +
                          "   AND B.category_id = C.category_id " +
                          "   AND B.lecture_name like :lectureName " +
                          " GROUP BY " +
                          "       B.lecture_id " +
                          "     , B.lecture_name " +
                          "     , B.education_period_start_date " +
                          "     , B.education_period_end_date " +
                          "     , C.category_name " +
                          " UNION ALL " +
                          "SELECT B.lecture_id " +
                          "     , B.lecture_name " +
                          "     , B.education_period_start_date " +
                          "     , B.education_period_end_date " +
                          "     , C.category_name " +
                          "     , 0 as course_registration_cnt " +
                          "  FROM category C " +
                          "     , lecture  B " +
                          " WHERE B.category_id = C.category_id " +
                          "   AND B.lecture_name like :lectureName " +
                          "   AND NOT EXISTS ( SELECT * " +
                          "                      FROM course_registration A " +
                          "                     WHERE A.lecture_id = B.lecture_id ) " +
                          " ORDER BY 6 desc, 1 ";
        Query query = entityManager.createNativeQuery(sqlQuery);
        query.setParameter("lectureName", lectureName); // 전체는 %, 조건이 있으면 %조건%

        return query.getResultList(); // Returns a list of Object arrays
    }

    public List<CourseEnrollCountDto> getCourseEnrollCountQueryJPQL(String lectureName) {

        List<CourseEnrollCountDto> targetDto = new ArrayList<>();
        List<Object[]> results = executeNativeQueryDto2(lectureName);

// [CourseEnrollCountDto]
//        private String lectureId;
//        private String lectureName;
//        private String lectureStartDate;
//        private String lectureEndDate;
//        private String categoryName;
//        private Long courseEnrollCount;

        // Process the results
        for (Object[] row : results) {
            // Access each column in the row
            System.out.println("lectureId: "        +row[0].toString());
            System.out.println("lectureName: "      +row[1].toString());
            System.out.println("lectureStartDate: " +row[2].toString());
            System.out.println("lectureEndDate: "   +row[3].toString());
            System.out.println("categoryName: "     +row[4].toString());
            System.out.println("courseEnrollCount: "+row[5].toString());

            String  column1Value = row[0].toString(); // Assuming column1 is of type String
            String  column2Value = row[1].toString(); // Assuming column2 is of type String
            String  column3Value = row[2].toString(); // Assuming column3 is of type String
            String  column4Value = row[3].toString(); // Assuming column4 is of type String
            String  column5Value = row[4].toString(); // Assuming column5 is of type String
            Long    column6Value = Long.valueOf(row[5].toString()); // Assuming column6 is of type int

            System.out.println("column1Value: "+ column1Value);
            System.out.println("column2Value: "+ column2Value);
            System.out.println("column3Value: "+ column3Value);
            System.out.println("column4Value: "+ column4Value);
            System.out.println("column5Value: "+ column5Value);
            System.out.println("column6Value: "+ column6Value);

            CourseEnrollCountDto courseEnrollCountDto = new CourseEnrollCountDto(column1Value, column2Value, column3Value, column4Value, column5Value, column6Value);
            targetDto.add(courseEnrollCountDto);
            // Do something with the values...
        }

        return targetDto;
    }

    public List<Object[]> executeNativeQueryDto1(String userId) {
        String sqlQuery = "select 'A'        as lecture_status_id " +
                          "     , '수강신청' as lecture_status_name " +
                          "     , count(*)   as lecture_status_count " +
                          "     , 0          as sort_seq " +
                          "  from course_registration a " +
                          " where a.user_id = :userId " +
                          " union all " +
                          "select a.lecture_status as lecture_status_id " +
                          "     , b.code_name      as lecture_status_name " +
                          "     , count(*)         as category_count " +
                          "     , b.sort_seq       as sort_seq " +
                          "  from code_class_detail   b " +
                          "     , course_registration a " +
                          " where b.code_class = 'LECTURE_STATUS' " +
                          "   and a.lecture_status = b.code " +
                          "   and a.user_id = :userId " +
                          " group by " +
                          "       a.lecture_status " +
                          "     , b.code_name " +
                          "     , b.sort_seq " +
                          " union all " +
                          "select a.code      as lecture_status_id " +
                          "     , a.code_name as lecture_status_name " +
                          "     , 0           as category_count " +
                          "     , a.sort_seq  as sort_seq " +
                          "  from code_class_detail a " +
                          " where a.code_class = 'LECTURE_STATUS' " +
                          "   and not exists ( select * " +
                          "                      from course_registration b " +
                          "                     where b.user_id = :userId " +
                          "                       and b.lecture_status = a.code ) " +
                          " order by 4 ";
        Query query = entityManager.createNativeQuery(sqlQuery);
        query.setParameter("userId", userId);

        return query.getResultList(); // Returns a list of Object arrays
    }

    // Native Query 사용2 => Dto 에 담는 예제
    public List<LectureStatusCountDto> getLectureStatusCountJPQL(String userId) {

        List<LectureStatusCountDto> targetDto = new ArrayList<>();
        List<Object[]> results = executeNativeQueryDto1(userId);

// [LectureCategoryCountDto]
//    private String LectureStatusId;
//    private String LectureStatusName;
//    private Long LectureStatusCount;

        // Process the results
        for (Object[] row : results) {
            // Access each column in the row
            System.out.println("StatusId:    "+row[0].toString());
            System.out.println("StatusName:  "+row[1].toString());
            System.out.println("StatusCount: "+row[2].toString());
            System.out.println("SortSeq:     "+row[3].toString());

            String  column1Value = row[0].toString(); // Assuming column1 is of type String
            String  column2Value = row[1].toString(); // Assuming column2 is of type String
            Long    column3Value = Long.valueOf(row[2].toString()); // Assuming column3 is of type int
            Long    column4Value = Long.valueOf(row[3].toString()); // Assuming column3 is of type int

            System.out.println("column1Value: "+ column1Value);
            System.out.println("column2Value: "+ column2Value);
            System.out.println("column3Value: "+ column3Value);
            System.out.println("column4Value: "+ column4Value);

            LectureStatusCountDto lectureStatusCountDto = new LectureStatusCountDto(column1Value, column2Value, column3Value, column4Value);
            targetDto.add(lectureStatusCountDto);
            // Do something with the values...
        }

        return targetDto;
    }

}
