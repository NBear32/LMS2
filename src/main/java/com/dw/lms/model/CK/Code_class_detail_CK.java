package com.dw.lms.model.CK;

import java.io.Serializable;

import com.dw.lms.model.Code_class;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@EqualsAndHashCode
public class Code_class_detail_CK implements Serializable {
    private Code_class codeClass;
    private String code;
}
