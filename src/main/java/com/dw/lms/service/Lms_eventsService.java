package com.dw.lms.service;

import com.dw.lms.model.Lms_events;
import com.dw.lms.repository.Lms_eventsRepository;
import com.dw.lms.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Lms_eventsService {
    Lms_eventsRepository lms_eventsRepository;
    UserRepository userRepository;

    public Lms_eventsService(Lms_eventsRepository lms_eventsRepository, UserRepository userRepository) {
        this.lms_eventsRepository = lms_eventsRepository;
        this.userRepository = userRepository;
    }

    public List<Lms_events> getAllLms_events() {
        return lms_eventsRepository.findAll();
    }


}
