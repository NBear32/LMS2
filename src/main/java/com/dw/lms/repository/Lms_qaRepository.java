package com.dw.lms.repository;

import com.dw.lms.model.Lms_qa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Lms_qaRepository extends JpaRepository<Lms_qa, Long> {
    Page<Lms_qa> findByCategoryId(String categoryId, PageRequest pageRequest);

    Page<Lms_qa> findByLmsQaTitleContainingOrLmsQaContentContaining(String titleKeyword, String contentKeyword, Pageable pageable);
}
