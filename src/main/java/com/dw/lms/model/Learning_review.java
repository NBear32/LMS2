package com.dw.lms.model;

import com.dw.lms.model.CK.Learning_review_CK;
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
@IdClass(Learning_review_CK.class)
@Table(name="learning_review")
public class Learning_review {

    @Id
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "userId", referencedColumnName = "user_id"),
            @JoinColumn(name = "lectureId", referencedColumnName = "lecture_id")
    })
    private Course_registration course_registration;

    @Column(name = "learning_review_date")
    private LocalDate learningReviewDate;

    @Column(name = "learning_review_score")
    private Long learningReviewScore;

    @Column(name = "learning_review_title", length = 100)
    private String learningReviewTitle;

    @Column(name = "learning_review_content", length = 1500)
    private String learningReviewContent;

    @Column(name = "sys_date", updatable = false)
    private LocalDateTime sysDate;

    @Column(name = "upd_date")
    private LocalDateTime updDate;
}
