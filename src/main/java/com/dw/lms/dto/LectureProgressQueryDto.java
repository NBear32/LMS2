package com.dw.lms.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LectureProgressQueryDto {
    private Long learning_contents_seq;
    private String learning_contents;
    private Long progress_rate;
    private Long learning_count;
    private String last_learning_datetime;
    private String complete_learning_datetime;
    private String learning_time;
    private String learning_playtime;
    private String learning_pdf_path;
    private String learning_video_path;
}
