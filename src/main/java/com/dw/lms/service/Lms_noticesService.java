package com.dw.lms.service;

import com.dw.lms.model.Lms_notices;
import com.dw.lms.repository.Lms_noticesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Lms_noticesService {
    @Autowired
    private Lms_noticesRepository repository;

    public Page<Lms_notices> getAllNotices(int page, int size) {
        return repository.findAll(PageRequest.of(page, size));
    }

    public Lms_notices getNoticeById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Lms_notices saveNotice(Lms_notices notice) {
        return repository.save(notice);
    }

    public void deleteNotice(Long id) {
        repository.deleteById(id);
    }
}