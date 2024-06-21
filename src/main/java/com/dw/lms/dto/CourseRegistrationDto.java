package com.dw.lms.dto;

import com.dw.lms.model.Lecture;
import com.dw.lms.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CourseRegistrationDto {

    private User user;
    private Lecture lecture;
    private LocalDate courseRegistrationDate;
    private Long finalLectureContentsSeq;
    private Long progressLectureContentsSeq;
    private String lectureCompletedCheck;
    private LocalDateTime sysDate;
    private LocalDateTime updDate;

}
