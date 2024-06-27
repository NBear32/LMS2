package com.dw.lms.controller;

import com.dw.lms.model.Lms_notices;
import com.dw.lms.service.Lms_noticesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notices")
public class Lms_noticesController {
    @Autowired
    private Lms_noticesService service;

    @GetMapping
    public Page<Lms_notices> getAllNotices(@RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "5") int size) {
        return service.getAllNotices(page, size);
    }

    @GetMapping("/{id}")
    public Lms_notices getNoticeById(@PathVariable Long id) {
        return service.getNoticeById(id);
    }

    @PostMapping
    public Lms_notices createNotice(@RequestBody Lms_notices notice) {
        return service.saveNotice(notice);
    }

    @PutMapping("/{id}")
    public Lms_notices updateNotice(@PathVariable Long id, @RequestBody Lms_notices notice) {
        notice.setLmsNoticesSeq(id);
        return service.saveNotice(notice);
    }

    @DeleteMapping("/{id}")
    public void deleteNotice(@PathVariable Long id) {
        service.deleteNotice(id);
    }
}