package com.dw.lms.dto;

import com.dw.lms.model.Authority;
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
public class UserDto { // Login Dto 2개(userId, password) 필요, Sign-up Dto 4개 필요
    private String userId;
    private String password;
    private String userName;
    private String userEmail;
    private String userNameEng;
    private String gender;
    private LocalDate birthDate;
    private String hpTel;
    private String zip_code;
    private String address1Name;
    private String address2Name;
    private String education;
    private String finalSchool;
    private String cfOfEmp;
    private String consentToRiYn;
    private String receiveEmailYn;
    private String receiveSmsYn;
    private String receiveAdsPrPromoYn;
    private String actYn;
    private LocalDateTime updatedAt;
    private Authority authority;
}
