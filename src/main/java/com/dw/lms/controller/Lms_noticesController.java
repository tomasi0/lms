package com.dw.lms.controller;

import com.dw.lms.model.Lms_notices;
import com.dw.lms.service.Lms_eventsService;
import com.dw.lms.service.Lms_noticesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class Lms_noticesController {
    Lms_noticesService lms_noticesService;

    public Lms_noticesController(Lms_noticesService lms_noticesService) {
        this.lms_noticesService = lms_noticesService;
    }

    @GetMapping("/products/notices")
    public ResponseEntity<List<Lms_notices>> getAllLms_notices() {
        return new ResponseEntity<>(lms_noticesService.getAllLms_notices(),
                HttpStatus.OK);
    }


}
