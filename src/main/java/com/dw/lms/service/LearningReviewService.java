package com.dw.lms.service;

import com.dw.lms.model.CK.Course_registration_CK;
import com.dw.lms.model.Course_registration;
import com.dw.lms.model.Learning_review;
import com.dw.lms.model.Lecture;
import com.dw.lms.model.User;
import com.dw.lms.repository.CourseRegistrationRepository;
import com.dw.lms.repository.LearningReviewRepository;
import com.dw.lms.repository.LectureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class LearningReviewService {
    @Autowired
    LearningReviewRepository learningReviewRepository;
    @Autowired
    LectureRepository lectureRepository;
    @Autowired
    CourseRegistrationRepository courseRegistrationRepository;

    public List<Learning_review> getAllReview() {
        return learningReviewRepository.findAll();
    }




}

