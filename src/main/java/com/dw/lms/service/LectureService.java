package com.dw.lms.service;

import com.dw.lms.model.Course_registration;
import com.dw.lms.model.Lecture;
import com.dw.lms.repository.LectureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LectureService {
    @Autowired
    LectureRepository lectureRepository;

    public List<Lecture> getAllLecture() {
        return lectureRepository.findAll();
    }
}
