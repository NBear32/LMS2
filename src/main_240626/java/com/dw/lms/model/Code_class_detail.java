package com.dw.lms.model;

import com.dw.lms.model.CK.Code_class_detail_CK;
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
@IdClass(Code_class_detail_CK.class)
@Table(name="code_class_detail")
public class Code_class_detail {
  @Id
  @ManyToOne
  @JoinColumn(name = "code_class")
  private Code_class codeClass;

  @Id
  @Column(name = "code", length = 2)
  private String code;

  @Column(name = "code_name", length = 100)
  private String codeName;

  @Column(name = "sort_seq")
  private Long sortSeq;

  @Column(name = "sys_date", updatable = false)
  private LocalDateTime sysDate;

  @Column(name = "upd_date")
  private LocalDateTime updDate;

}
