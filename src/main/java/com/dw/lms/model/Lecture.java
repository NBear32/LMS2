package com.dw.lms.model;

import jakarta.persistence.*;
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
@Entity
@Table(name="lecture")
public class Lecture {
    @Id
    @Column(name = "lecture_id", length = 12)
    private String lectureId;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "image_path", length = 255)
    private String imagePath;

    @Column(name = "lecture_name", length = 255)
    private String lectureName;

    @Column(name = "interested_check", length = 1)
    private String interestedCheck;

    @Column(name = "education_period")
    private Long educationPeriod;

    @Column(name = "education_hours")
    private Long educationHours;

    @Column(name = "application_period_start_date")
    private LocalDate applicationPeriodStartDate;

    @Column(name = "application_period_end_date")
    private LocalDate applicationPeriodEndDate;

    @Column(name = "education_period_start_date")
    private LocalDate educationPeriodStartDate;

    @Column(name = "education_period_end_date")
    private LocalDate educationPeriodEndDate;

    @Column(name = "education_price")
    private Long educationPrice;

    @Column(name = "education_overview", length = 1000)  /* 교육개요 */
    private String educationOverview;

    @Column(name = "learning_objectives", length = 1500) /* 학습목표 */
    private String learningObjectives;

    @Column(name = "learning_object", length = 500)      /* 학습대상 */
    private String learningObject;

    @Column(name = "textbook_information", length = 500) /* 교재정보 */
    private String textbookInformation;

    @Column(name = "completion_criteria_total_point", length = 100)
    private String completionCriteriaTotalPoint;

    @Column(name = "completion_criteria_items", length = 100)
    private String completionCriteriaItems;

    @Column(name = "completion_criteria_reflection_ratio", length = 100)
    private String completionCriteriaReflectionRatio;

    @Column(name = "completion_criteria_base", length = 100)
    private String completionCriteriaBase;

    @Column(name = "sys_date", updatable = false)
    private LocalDateTime sysDate;

    @Column(name = "upd_date")
    private LocalDateTime updDate;


}
