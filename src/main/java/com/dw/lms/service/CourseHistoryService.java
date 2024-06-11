package com.dw.lms.service;

import com.dw.lms.model.Category;
import com.dw.lms.model.Course_history;
import com.dw.lms.repository.CourseHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseHistoryService {
    @Autowired
    CourseHistoryRepository courseHistoryRepository;

    public List<Course_history> getAllHistory() {
        return courseHistoryRepository.findAll();
    }
}
