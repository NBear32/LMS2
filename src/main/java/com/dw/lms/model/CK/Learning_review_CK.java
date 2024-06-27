package com.dw.lms.model.CK;

import com.dw.lms.model.Course_registration;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@EqualsAndHashCode
public class Learning_review_CK implements Serializable {
    private Course_registration course_registration;
}
