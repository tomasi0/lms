package com.dw.lms.controller;

import com.dw.lms.model.Lms_qa;
import com.dw.lms.service.Lms_qaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/qa")
public class Lms_qaController {
    Lms_qaService lms_qaService;

    public Lms_qaController(Lms_qaService lms_qaService) {
        this.lms_qaService = lms_qaService;
    }

    @GetMapping("/getAllItems")
    public ResponseEntity<List<Lms_qa>> getAllLms_qa() {

        System.out.println("getAllItems");

        return new ResponseEntity<>(lms_qaService.getAllLms_qa(),
                HttpStatus.OK);
    }



}
