package com.dw.lms.model;

import com.dw.lms.model.CK.Learning_contents_CK;
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
@IdClass(Learning_contents_CK.class)
@Table(name="learning_contents")
public class Learning_contents {
    @Id
    @ManyToOne
    @JoinColumn(name = "lecture_id")
    private Lecture lecture;

    @Id
    @Column(name = "learning_contents_seq")
    private Long learningContentsSeq;

    @Column(name = "learning_contents", length = 255)
    private String learningContents;

    @Column(name = "learning_playtime", length = 6)
    private String learningPlaytime;

    @Column(name = "learning_video_path", length = 255)
    private String learningVideo_path;

    @Column(name = "learning_pdf_path", length = 255)
    private String learningPdf_path;

    @Column(name = "sys_date", updatable = false)
    private LocalDateTime sysDate;

    @Column(name = "upd_date")
    private LocalDateTime updDate;
}
