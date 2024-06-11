package com.dw.lms.controller;

import com.dw.lms.model.Lms_events;
import com.dw.lms.service.Lms_eventsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class Lms_eventsController {
    Lms_eventsService lms_eventsService;

    public Lms_eventsController(Lms_eventsService lms_eventsService) {
        this.lms_eventsService = lms_eventsService;
    }

    @GetMapping("/products/events")
    public ResponseEntity<List<Lms_events>> getAllLms_events() {
        return new ResponseEntity<>(lms_eventsService.getAllLms_events(),
                HttpStatus.OK);
    }
}
