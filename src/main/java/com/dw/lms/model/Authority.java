package com.dw.lms.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity // 없으면 JPA 가 Table 생성을 안해줌
@Table(name = "authority")
public class Authority {
    @Id
    @Column(name = "authority_name", length = 50)
    private String authorityName;

    @Column(name = "sys_date")
    private LocalDateTime sysDate;

    @Column(name = "upd_date")
    private LocalDateTime updDate;

    public String getAuthorityName() {
        return authorityName;
    }
}
