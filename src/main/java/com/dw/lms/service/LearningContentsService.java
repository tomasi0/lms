package com.dw.lms.service;

import com.dw.lms.model.Learning_contents;
import com.dw.lms.repository.LearningContentsRepository;
import com.dw.lms.repository.LectureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LearningContentsService {
    @Autowired
    LearningContentsRepository learningContentsRepository;

    @Autowired
    LectureRepository lectureRepository;

    public List<Learning_contents> getAllContents() {
        return learningContentsRepository.findAll();
    }
    
    public List<Learning_contents> getContentsByLectureId(String lectureId){
        // sort는 JPA에서 제공하는 정렬 옵션
        // sort = 오름차순 or 내림차순 DESC -> 내림차순 ASC -> 오름차순
        Sort sort = Sort.by(Sort.Direction.ASC, "learningContentsSeq");
        return learningContentsRepository.findByLecture_LectureId(lectureId, sort);
    }

}
