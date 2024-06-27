package com.dw.lms.model.CK;

import com.dw.lms.model.User;
import com.dw.lms.model.Lecture;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@EqualsAndHashCode
public class Teacher_CK implements Serializable {
    private User user;
    private Lecture lecture;
}
