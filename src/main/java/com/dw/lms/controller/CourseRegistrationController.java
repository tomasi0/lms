package com.dw.lms.controller;

import com.dw.lms.dto.LectureCategoryCountDto;
import com.dw.lms.dto.LectureStatusCountDto;
import com.dw.lms.model.Course_history;
import com.dw.lms.model.Course_registration;
import com.dw.lms.repository.CourseRegistrationRepository;
import com.dw.lms.service.CourseRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CourseRegistrationController {
    @Autowired
    CourseRegistrationService CourseRegistrationService;

    @GetMapping("/course/registration")
    public ResponseEntity<List<Course_registration>> getAllRegistration() {
        return new ResponseEntity<>(CourseRegistrationService.getAllRegistration(),
                HttpStatus.OK);
    }

    @GetMapping("/course/lectureStatusCount/id/{userId}")
    public ResponseEntity<List<LectureStatusCountDto>> getLectureStatusCountJPQL(@PathVariable String userId) {
        return new ResponseEntity<>(CourseRegistrationService.getLectureStatusCountJPQL(userId),
                HttpStatus.OK);
    }

}