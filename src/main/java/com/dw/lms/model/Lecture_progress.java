package com.dw.lms.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="lecture_progress")
public class Lecture_progress {

//    [lecture_progress]
//    lecture_progress_seq
//    user_id
//    lecture_id
//    learning_contents_seq
//    progress_rate
//    learning_count
//    last_learning_datetime
//    complet_learning_datetime
//    learning_time
//    standard_time
//    upd_date
//    sys_date

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lecture_progress_seq")
    private Long lectureProgressSeq;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name="user_id", referencedColumnName = "user_id"),
            @JoinColumn(name="lecture_id", referencedColumnName = "lecture_id")
    })
    private Course_registration course_registration;

    @Column(name = "learning_contents_seq")
    private Long learningContentsSeq;

    @Column(name = "progress_rate")
    private Long progressRate;

    @Column(name = "learning_count")
    private Long learningCount;

    @Column(name = "last_learning_datetime")
    private LocalDateTime lastLearningDatetime;

    @Column(name = "complete_learning_datetime")
    private LocalDateTime completeLearningDatetime;

    @Column(name = "learning_time", length = 6)
    private String learningTime;

    @Column(name = "sys_date", updatable = false)
    private LocalDateTime sysDate;

    @Column(name = "upd_date")
    private LocalDateTime updDate;
}
