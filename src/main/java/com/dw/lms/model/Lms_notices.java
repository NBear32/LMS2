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
@Table(name="lms_notices")
public class Lms_notices {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lms_notices_seq")
    private Long lmsNoticesSeq;

    @Column(name = "category_id", length = 2)
    private String categoryId;

    @Column(name = "lms_notices_title", length = 100)
    private String lmsNoticesTitle;

    @Column(name = "lms_notices_content", length = 2500)
    private String lmsNoticesContent;

    @ManyToOne
    @JoinColumn(name = "lms_notices_writer")
    private User user;

    @Column(name = "lms_notices_writing_date")
    private LocalDate lmsNoticesWritingDate;

    @Column(name = "lms_notices_view_count")
    private Long lmsNoticesViewCount;

    @Column(name = "sys_date", updatable = false)
    private LocalDateTime sysDate;

    @Column(name = "upd_date")
    private LocalDateTime updDate;
}
