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
    		// findAll() 사용시 조회 순서가 오름차순, repository 에서 Query 를 사용하여 내림차순 조회로 수정
        // List<Lecture> lecture = lectureRepository.findAll();
        return lectureRepository.getAllLecture();
    }
}
