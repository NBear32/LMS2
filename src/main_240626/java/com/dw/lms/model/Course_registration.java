package com.dw.lms.model;

import com.dw.lms.model.CK.Course_registration_CK;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@IdClass(Course_registration_CK.class)
@Table(name="course_registration")
public class Course_registration {
    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "lecture_id")
    private Lecture lecture;

    @Column(name = "course_registration_date")
    private LocalDate courseRegistrationDate;

    @Column(name = "final_lecture_contents_seq")
    private Long finalLectureContentsSeq;

    @Column(name = "progress_lecture_contents_seq")
    private Long progressLectureContentsSeq;

    @Column(name = "lecture_status", length = 1, nullable = false)
    @ColumnDefault("'I'")
    private String lectureStatus;

    @Column(name = "lecture_completed_check", length = 1)
    @ColumnDefault("'N'")
    private String lectureCompletedCheck;

    @Column(name = "sys_date", updatable = false)
    private LocalDateTime sysDate;

    @Column(name = "upd_date")
    private LocalDateTime updDate;
}
