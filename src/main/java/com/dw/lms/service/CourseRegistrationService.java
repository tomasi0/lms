package com.dw.lms.service;

import com.dw.lms.model.Course_history;
import com.dw.lms.model.Course_registration;
import com.dw.lms.repository.CourseRegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseRegistrationService {
    @Autowired
    CourseRegistrationRepository courseRegistrationRepository;

    public List<Course_registration> getAllRegistration() {
        return courseRegistrationRepository.findAll();
    }
}
