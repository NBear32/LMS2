package com.dw.lms.model.CK;

import com.dw.lms.model.Lecture;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@EqualsAndHashCode
public class Learning_contents_CK implements Serializable {
    private Lecture lecture;
    private Long learningContentsSeq;
}
