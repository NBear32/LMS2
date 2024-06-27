package com.dw.lms.model;

import com.dw.lms.model.CK.Course_history_CK;
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
@IdClass(Course_history_CK.class)
@Table(name="course_history")
public class Course_history {
    @Id
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name="user_id", referencedColumnName = "user_id"),
            @JoinColumn(name="lecture_id", referencedColumnName = "lecture_id")
    })
    private Course_registration course_registration;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_history_seq")
    private Long courseHistorySeq;

    @Column(name = "connection_ip", length = 15)
    private String connectionIp;

    @Column(name = "connection_start_date")
    private LocalDateTime connectionStartDate;

    @Column(name = "connection_end_date")
    private LocalDateTime connectionEndDate;

    @Column(name = "sys_date", updatable = false)
    private LocalDateTime sysDate;

    @Column(name = "upd_date")
    private LocalDateTime updDate;
}
