package com.dw.lms.controller;

import com.dw.lms.model.Lms_events;
import com.dw.lms.service.Lms_eventsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/lmsevents")
public class Lms_eventsController {

    @Autowired
    private Lms_eventsService lmsEventsService;

    @PostMapping
    public ResponseEntity<Lms_events> createLmsEvent(@RequestBody Lms_events lmsEvent) {
        Lms_events savedEvent = lmsEventsService.saveLmsEvent(lmsEvent);
        return ResponseEntity.ok(savedEvent);
    }

    @GetMapping
    public ResponseEntity<Page<Lms_events>> getAllLmsEvents(@RequestParam int page, @RequestParam int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Lms_events> events = lmsEventsService.getAllLmsEvents(pageable);
        return ResponseEntity.ok(events);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Lms_events> getLmsEventById(@PathVariable Long id) {
        Optional<Lms_events> event = lmsEventsService.getLmsEventById(id);
        return event.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<Lms_events> updateLmsEvent(@PathVariable Long id, @RequestBody Lms_events lmsEventDetails) {
        Optional<Lms_events> optionalEvent = lmsEventsService.getLmsEventById(id);
        if (optionalEvent.isPresent()) {
            Lms_events event = optionalEvent.get();
            event.setImagePath(lmsEventDetails.getImagePath());
            event.setLmsEventsTitle(lmsEventDetails.getLmsEventsTitle());
            event.setLmsEventsContent(lmsEventDetails.getLmsEventsContent());
            event.setLmsEventsStartDate(lmsEventDetails.getLmsEventsStartDate());
            event.setLmsEventEndDate(lmsEventDetails.getLmsEventEndDate());
            event.setLmsEventsWritingDate(lmsEventDetails.getLmsEventsWritingDate());
            event.setLmsEventViewCount(lmsEventDetails.getLmsEventViewCount());
            event.setSysDate(lmsEventDetails.getSysDate());
            event.setUpdDate(lmsEventDetails.getUpdDate());
            Lms_events updatedEvent = lmsEventsService.saveLmsEvent(event);
            return ResponseEntity.ok(updatedEvent);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<Void> deleteLmsEvent(@PathVariable Long id) {
        lmsEventsService.deleteLmsEvent(id);
        return ResponseEntity.noContent().build();
    }
}