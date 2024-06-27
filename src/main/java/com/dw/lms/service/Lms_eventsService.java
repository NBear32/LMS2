package com.dw.lms.service;

import com.dw.lms.model.Lms_events;
import com.dw.lms.repository.Lms_eventsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class Lms_eventsService {

    @Autowired
    private Lms_eventsRepository lmsEventsRepository;

    public Lms_events saveLmsEvent(Lms_events lmsEvent) {
        return lmsEventsRepository.save(lmsEvent);
    }

    public Page<Lms_events> getAllLmsEvents(Pageable pageable) {
        return lmsEventsRepository.findAll(pageable);
    }

    public Optional<Lms_events> getLmsEventById(Long id) {
        return lmsEventsRepository.findById(id);
    }

    public void deleteLmsEvent(Long id) {
        lmsEventsRepository.deleteById(id);
    }
}