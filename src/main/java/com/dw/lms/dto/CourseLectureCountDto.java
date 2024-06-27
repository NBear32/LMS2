package com.dw.lms.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CourseLectureCountDto {
    private String userId;
    private String userName;
    private String userEmail;
    private String actYn;
    private Long courseLectureCount;
}
