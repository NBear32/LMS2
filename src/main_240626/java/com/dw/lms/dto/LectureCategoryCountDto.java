package com.dw.lms.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LectureCategoryCountDto {
    private String categoryId;
    private String categoryName;
    private Long categoryCount;
}
