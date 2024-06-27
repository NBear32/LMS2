package com.dw.lms.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CourseEnrollCountDto {
    private String lectureId;
    private String lectureName;
    private String lectureStartDate;
    private String lectureEndDate;
    private String categoryName;
    private Long courseEnrollCount;
}
