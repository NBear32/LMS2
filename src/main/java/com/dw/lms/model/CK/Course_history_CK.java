package com.dw.lms.model.CK;

import com.dw.lms.model.Course_registration;
import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Course_history_CK implements Serializable {
    private Course_registration course_registration;
    private Long courseHistorySeq;
}
