package com.dw.lms.repository;

import com.dw.lms.model.CK.Code_class_detail_CK;
import com.dw.lms.model.Code_class_detail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Code_class_detailRepository extends JpaRepository<Code_class_detail, Code_class_detail_CK> {
}
